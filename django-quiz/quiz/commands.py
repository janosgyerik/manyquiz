import re

from django.db import transaction

from quiz.models import Question, Level, Answer

re_question = re.compile(r'^[A-Z]')
re_answer = re.compile(r'.')
re_level = re.compile(r'^\d$')
re_explanation = re.compile(r'^[A-Z].*\.$')
re_category = re.compile(r'^[\w-]+$')
re_end = re.compile(r'^END$')


class MalformedInput(Exception):
    def __init__(self, errors, lines):
        labels = ('Question', 'Correct answer', 'Answer 2', 'Answer 3', 'Answer 4', 'Explanation', 'Level', 'Category', 'END')
        while len(lines) < len(labels):
            lines.append(None)
        message = 'Errors in the input data:\n  '
        message += '\n  '.join(errors) + '\n'
        message += 'Input data:\n'
        for k, v in zip(labels, lines):
            message += '  %-15s: %s\n' % (k, v)
        super(MalformedInput, self).__init__(message)


class IncompleteInput(MalformedInput):
    def __init__(self, lines):
        errors = ('End of file before end marker',)
        super(IncompleteInput, self).__init__(errors, lines)


def reset():
    Question.objects.all().delete()


def _get_level(difficulty):
    try:
        level = Level.objects.get(level=difficulty)
    except Level.DoesNotExist:
        level = Level(level=difficulty, name='Level %s' % difficulty)
        level.save()
    return level


def is_question(line):
    return re_question.match(line) is not None


def is_answer(line):
    return re_answer.match(line) is not None


def is_explanation(line):
    return re_explanation.match(line) is not None


def is_level(line):
    return re_level.match(line) is not None


def is_category(line):
    return re_category.match(line) is not None


def is_end(line):
    return re_end.match(line) is not None


def parse_file(path, stdout, fun):
    lines = []
    with open(path) as instream:
        for line in instream:
            lines.append(line.strip())
            if len(lines) == 9:
                fun(stdout, *lines)
                lines = []
    if len(lines) > 0:
        raise IncompleteInput(lines)


def check_lines(stdout, *lines):
    (text, a1, a2, a3, a4,
            explanation, level, category, endmarker) = lines
    errors = []
    if not is_question(text):
        errors.append('Malformed question: %s' % text)
    for ax in (a1, a2, a3, a4):
        if not is_answer(ax):
            errors.append('Malformed answer: %s' % ax)
    if not is_level(level):
        errors.append('Malformed level: %s' % level)
    if not is_category(category):
        errors.append('Malformed category: %s' % category)
    if not is_end(endmarker):
        errors.append('Malformed end marker: %s' % endmarker)
    if errors:
        raise MalformedInput(errors, lines)
    level = _get_level(level)
    question = Question(
            text=text,
            category=category,
            level=level,
            explanation=explanation,
            )
    correct_answer = Answer(question=question, text=a1, is_correct=True)
    other_answers = []
    for ax in a2, a3, a4:
        answer = Answer(question=question, text=ax)
        other_answers.append(answer)
    return question, correct_answer, other_answers


def check_file(path, stdout):
    parse_file(path, stdout, _check_only)


def import_file(path, stdout):
    parse_file(path, stdout, _check_and_import)


def _check_only(stdout, *lines):
    check_lines(stdout, *lines)


@transaction.commit_on_success
def _check_and_import(stdout, *lines):
    (question, correct_answer, other_answers) = check_lines(stdout, *lines)
    try:
        Question.objects.get(text=question.text, level=question.level)
        stdout.write('* question exists, skipping: %s' % question)
        return
    except:
        pass
    stdout.write('* saving question: %s' % question)
    question.save()
    correct_answer.question = question
    correct_answer.save()
    for ax in other_answers:
        ax.question = question
        ax.save()
