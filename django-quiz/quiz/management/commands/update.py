from optparse import make_option

from django.core.management.base import BaseCommand

from quiz.models import Question, Level


class Command(BaseCommand):
    help = 'Update quiz data'

    option_list = BaseCommand.option_list + (
            make_option('--set-level', type='int', dest='level',
                help='Set level on all questions'),
            make_option('--delete-category',
                help='Delete specified category'),
            )

    def handle(self, *args, **options):
        if options['level']:
            level = Level.objects.get(level=options['level'])
            Question.objects.update(level=level)

        if options['delete_category']:
            category = options['delete_category']
            Question.objects.filter(category=category).delete()
