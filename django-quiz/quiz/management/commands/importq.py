import os
from optparse import make_option

from django.core.management.base import BaseCommand

from quiz.models import Question, Answer, Level

END_MARKER = 'END'


def msg(*args):
    print '[*]', ' '.join([str(x) for x in args])


def warn(*args):
    print '[WARNING]', ' '.join([str(x) for x in args])


def get_level(difficulty):
    try:
        level = Level.objects.get(level=difficulty)
    except Level.DoesNotExist:
        level = Level(level=difficulty, name='Level %s' % difficulty)
        level.save()
    return level


def import_file(path, dry_run=False):
    i = 0
    data = []
    instream = open(path)
    for line in instream:
        data.append(line.strip())
        i += 1
        if i % 9 == 0:
            (text, a1, a2, a3, a4,
             explanation, difficulty, image, marker) = data
            if marker != END_MARKER:
                warn('end marker expected but found: "%s"' % marker)
                warn('current segment:')
                print '\n'.join(data)
                warn('aborting')
                return
            data = []
            level = get_level(difficulty)
            try:
                question = Question.objects.get(
                        text=text,
                        category=image,
                        level=level,
                        #hint=None,
                        explanation=explanation,
                        )
                msg('skipping already imported:', question)
                continue
            except Question.DoesNotExist:
                pass
            question = Question(
                    text=text,
                    category=image,
                    level=get_level(difficulty),
                    #hint=None,
                    explanation=explanation,
                    )
            print 'Question:', question
            if not dry_run:
                question.save()
            answer = Answer(question=question, text=a1, is_correct=True)
            print '   *', answer
            if not dry_run:
                answer.save()
            for ax in a2, a3, a4:
                answer = Answer(question=question, text=ax)
                print '    ', answer
                if not dry_run:
                    answer.save()
            print


class Command(BaseCommand):
    help = 'Import old quiz data'

    option_list = BaseCommand.option_list + (
            make_option('--reset', action='store_true',
                help='Reset the database before import'),
            make_option('--dry-run', '-n', action='store_true',
                help='Do not really import, just print what would happen'),
            make_option('--check', action='store_true',
                help='Check that the import file is well-formatted'),
            )

    def handle(self, *args, **options):
        if options['reset']:
            msg('reset: deleting all questions (and answers)')
            Question.objects.all().delete()
        for path in args:
            if os.path.isfile(path):
                self.check_file(path)
                if not options['check']:
                    import_file(path, options['dry_run'])

    def check_file(self, path):
        ok = True
        i = 0
        data = []
        instream = open(path)
        for line in instream:
            data.append(line.strip())
            i += 1
            if i % 9 == 0:
                (text, a1, a2, a3, a4,
                 explanation, difficulty, image, marker) = data
                if marker != END_MARKER:
                    warn('end marker expected but found: "%s"' % marker)
                    warn('current segment:')
                    print '\n'.join(data)
                    ok = False
                data = []
                level = get_level(difficulty)
                question = Question(
                        text=text,
                        category=image,
                        level=get_level(difficulty),
                        #hint=None,
                        explanation=explanation,
                        )
                answer = Answer(question=question, text=a1, is_correct=True)
                for ax in a2, a3, a4:
                    answer = Answer(question=question, text=ax)
        if ok:
            print 'looks ok'
        else:
            print 'warnings detected, see above'
