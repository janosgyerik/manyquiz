from datetime import datetime

from django.db import models


class Level(models.Model):
    name = models.CharField(max_length=80, unique=True)
    level = models.IntegerField(unique=True)
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)

    def __unicode__(self):
        return '%s (%d)' % (self.name, self.level)


class Question(models.Model):
    text = models.CharField(max_length=200, unique=True)
    category = models.CharField(max_length=80, blank=True)
    level = models.ForeignKey(Level)
    hint = models.TextField(blank=True)
    explanation = models.TextField(blank=True)
    is_active = models.BooleanField(default=True)
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)

    def __unicode__(self):
        return self.text


class Answer(models.Model):
    question = models.ForeignKey(Question)
    text = models.CharField(max_length=200)
    is_correct = models.BooleanField()
    is_active = models.BooleanField(default=True)
    created_dt = models.DateTimeField(default=datetime.now)
    updated_dt = models.DateTimeField(default=datetime.now)

    class Meta:
        unique_together = (('question', 'text',))

    def __unicode__(self):
        return self.text

# eof
