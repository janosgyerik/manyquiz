import os
from optparse import make_option

from django.core.management.base import BaseCommand
from django.db.models import Count

from quiz.models import Question, Level


class Command(BaseCommand):
    help = 'Show stats about quiz data'

    option_list = BaseCommand.option_list + (
            make_option('--per-level', action='store_true',
                help='Show stats per level'),
            make_option('--per-category', action='store_true',
                help='Show stats per category'),
            )

    def handle(self, *args, **options):
        if options['per_level']:
            self.stdout.write('Questions per level:')
            for level in Level.objects.all():
                self.stdout.write('%5d -- %s' % (level.question_set.count(), level))
            self.stdout.write('')

        if options['per_category']:
            self.stdout.write('Questions per category:')
            for result in Question.objects.values('category').annotate(count=Count('id')).order_by():
                self.stdout.write('%(count)5d -- %(category)s' % result)
            self.stdout.write('')

        self.stdout.write('Total number of Questions:')
        self.stdout.write('%5d' % Question.objects.count())
        self.stdout.write('')


# eof
