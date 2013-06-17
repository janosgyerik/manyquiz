import os
from optparse import make_option

from django.core.management.base import BaseCommand
from django.db.models import Count

from quiz.models import Question, Level


class Command(BaseCommand):
    help = 'Show stats about quiz data'

    option_list = BaseCommand.option_list + (
            make_option('--levels', '-l', action='store_true',
                help='Show stats about levels'),
            make_option('--categories', '-c', action='store_true',
                help='Show stats about categories'),
            )

    def handle(self, *args, **options):
        if options['levels']:
            self.stdout.write('Questions per level:')
            for level in Level.objects.all():
                print '%5d -- %s' % (level.question_set.count(), level)
            self.stdout.write('')

        if options['categories']:
            self.stdout.write('Questions per category:')
            for result in Question.objects.values('category').annotate(count=Count('id')).order_by():
                print '%(count)5d -- %(category)s' % result
            self.stdout.write('')

        self.stdout.write('Total number of Questions:')
        self.stdout.write('%5d' % Question.objects.count())
        self.stdout.write('')


# eof
