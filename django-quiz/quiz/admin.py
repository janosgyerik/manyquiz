from django.contrib import admin

from quiz.models import Quiz
from quiz.models import Question
from quiz.models import Answer


class QuizAdmin(admin.ModelAdmin):
    list_display = ('name',)


class QuestionAdmin(admin.ModelAdmin):
    list_display = ('quiz', 'text', 'category', 'difficulty', 'is_active')
    list_filter = ('quiz', 'category', 'difficulty', 'is_active')


class AnswerAdmin(admin.ModelAdmin):
    list_display = ('question', 'text', 'is_correct', 'is_active')
    list_filter = ('question', 'is_correct', 'is_active')


admin.site.register(Quiz, QuizAdmin)
admin.site.register(Question, QuestionAdmin)
admin.site.register(Answer, AnswerAdmin)


# eof
