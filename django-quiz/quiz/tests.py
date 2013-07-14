from django.test import TestCase


class ImportQuestionTest(TestCase):
    def setUp(self):
        print 'hello'

    def test_valid(self):
        """
        Tests that 1 + 1 always equals 2.
        """
        self.assertEqual(1 + 1, 2)
