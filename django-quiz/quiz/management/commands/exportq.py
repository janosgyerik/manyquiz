from optparse import make_option

from django.core.management.base import BaseCommand

from quiz.models import Question

END_MARKER = 'END'


def export_questions(limit=10):
    query = Question.objects.all()
    if limit:
        query = query[:limit]
    for question in query.order_by('category'):
        print question.text
        for answer in question.answer_set.all().order_by('-is_correct'):
            print answer.text
        print question.explanation
        print question.level.level
        print question.category
        print END_MARKER


class Command(BaseCommand):
    help = 'Export questions in plain text format'

    option_list = BaseCommand.option_list + (
            make_option('--limit', '-l', type=int,
                help='Max number of questions to export'),
            )

    def handle(self, *args, **options):
        export_questions(options['limit'])


# eof
