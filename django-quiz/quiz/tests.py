import os

from django.test import TestCase
from quiz.management.commands.importq import Command

TESTS_DATA_DIR = os.path.join(os.path.dirname(__file__), 'tests-data')

class ImportQuestionTest(TestCase):
    def setUp(self):
        self.cmd = Command()

    def test_valid(self):
        self.assertTrue(self.cmd.check_file(self.get_testdata_path('valid.txt')))

    def get_testdata_path(self, filename):
        return os.path.join(TESTS_DATA_DIR, filename)
