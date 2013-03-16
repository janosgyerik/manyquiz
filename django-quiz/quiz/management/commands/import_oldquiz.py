import os
from optparse import make_option

from django.core.management.base import BaseCommand

from quiz.models import Question, Answer, Level


def msg(text):
    print '*', text


def get_level(difficulty):
    try:
        level = Level.objects.get(level=difficulty)
    except Level.DoesNotExist:
        level = Level(level=difficulty, name='Level %s' % difficulty)
        level.save()
    return level


def import_file(path):
    i = 0
    data = []
    instream = open(path)
    for line in instream:
        data.append(line.strip())
        i += 1
        if i % 10 == 0:
            (text, a1, a2, a3, a4, a0,
             explanation, difficulty, image, tmp) = data
            data = []
            question = Question(
                    text=text,
                    #category=
                    level=get_level(difficulty),
                    #hint=None,
                    explanation=explanation,
                    )
            print 'Question:', question
            question.save()
            for ax in a1, a2, a3, a4:
                answer = Answer(
                        question=question,
                        text=ax,
                        is_correct=ax == a0,
                        )
                answer.save()
                if answer.is_correct:
                    print '   *', answer
                else:
                    print '    ', answer
            print


class Command(BaseCommand):
    help = 'Import old quiz data'

    option_list = BaseCommand.option_list + (
            make_option('--reset', action='store_true',
                help='Reset the database before import'),
            )

    def handle(self, *args, **options):
        if options['reset']:
            msg('reset: deleting all questions (and answers)')
            Question.objects.all().delete()
        for path in args:
            if os.path.isfile(path):
                import_file(path)


# eof
