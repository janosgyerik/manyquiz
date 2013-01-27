from django.contrib import admin

from quiz.models import Level
from quiz.models import Question
from quiz.models import Answer


class LevelAdmin(admin.ModelAdmin):
    list_display = ('name', 'level')


class QuestionAdmin(admin.ModelAdmin):
    list_display = ('text', 'category', 'level', 'is_active')
    list_filter = ('category', 'level', 'is_active')


class AnswerAdmin(admin.ModelAdmin):
    list_display = ('question', 'text', 'is_correct', 'is_active')
    list_filter = ('question', 'is_correct', 'is_active')


admin.site.register(Level, LevelAdmin)
admin.site.register(Question, QuestionAdmin)
admin.site.register(Answer, AnswerAdmin)


# eof
