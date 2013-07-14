import os
import sys

from django.test import TestCase

from quiz.commands import check_file, MalformedInput

TESTS_DATA_DIR = os.path.join(os.path.dirname(__file__), 'tests-data')


class ImportQuestionTest(TestCase):
    def test_valid(self):
        self.check_file('valid.txt')

    def test_invalid(self):
        self.assertRaises(MalformedInput, self.check_file, 'invalid.txt')

    def check_file(self, filename):
        path = self.get_testdata_path(filename)
        check_file(path, sys.stdout)

    def get_testdata_path(self, filename):
        return os.path.join(TESTS_DATA_DIR, filename)
