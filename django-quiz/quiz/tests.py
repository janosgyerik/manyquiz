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

    def test_malformed_question(self):
        self.assertRaises(MalformedInput, self.check_file, 'malformed-question.txt')

    def test_malformed_level(self):
        self.assertRaises(MalformedInput, self.check_file, 'malformed-level.txt')

    def test_malformed_category(self):
        self.assertRaises(MalformedInput, self.check_file, 'malformed-category.txt')

    def test_malformed_endmarker(self):
        self.assertRaises(MalformedInput, self.check_file, 'malformed-endmarker.txt')

    def test_incomplete_input(self):
        self.assertRaises(MalformedInput, self.check_file, 'incomplete-input.txt')

    def check_file(self, filename):
        path = self.get_testdata_path(filename)
        check_file(path, sys.stdout)

    def get_testdata_path(self, filename):
        return os.path.join(TESTS_DATA_DIR, filename)
