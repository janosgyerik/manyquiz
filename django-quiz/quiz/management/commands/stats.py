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
            make_option('--levels', '-l',
                help='Filter by levels (comma separated)'),
            )

    def handle(self, *args, **options):
        if options['per_level']:
            self.stdout.write('Questions per level:')
            for level in Level.objects.all():
                self.stdout.write('%5d -- %s' % (level.question_set.count(), level))
            self.stdout.write('')

        if options['per_category']:
            q = Question.objects.all()
            if options['levels']:
                levels = options['levels'].split(',')
                q = q.filter(level__in=levels)
                self.stdout.write('# Levels = %s' % ', '.join(levels))
            self.stdout.write('Questions per category:')
            total = 0
            longest = 0
            for result in q.values('category').annotate(count=Count('id')).order_by():
                self.stdout.write('%(count)5d -- %(category)s' % result)
                total += result['count']
                if len(result['category']) > longest:
                    longest = len(result['category'])
            if total:
                self.stdout.write('         ' + '=' * longest)
                self.stdout.write('%5d -- Total' % total)
            self.stdout.write('')

        self.stdout.write('Total number of Questions:')
        self.stdout.write('%5d' % Question.objects.count())
        self.stdout.write('')


# eof
