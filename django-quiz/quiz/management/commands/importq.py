import os
from optparse import make_option

from django.core.management.base import BaseCommand

from quiz import commands


class Command(BaseCommand):
    help = 'Import quiz data from plain text files'

    option_list = BaseCommand.option_list + (
            make_option('--reset', action='store_true',
                help='Reset the database before import'),
            make_option('--check-only', action='store_true',
                help='Only check the import files are well-formatted'),
            )

    def handle(self, *args, **options):
        if options['reset']:
            self.msg('reset: deleting all questions (and answers)')
            commands.reset()
        for path in args:
            if os.path.isfile(path):
                if options['check_only']:
                    self.msg('checking file: %s' % path)
                    commands.check_file(path, self.stdout)
                else:
                    self.msg('importing file: %s' % path)
                    commands.import_file(path, self.stdout)
            else:
                self.warn('not a file: %s' % path)

    def msg(self, *args):
        self.stdout.write('[*] ' + ' '.join([str(x) for x in args]))

    def warn(self, *args):
        self.stdout.write('[WARNING] ' + ' '.join([str(x) for x in args]))
