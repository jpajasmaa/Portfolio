import datetime
from django.db import models
from django.utils import timezone


class Quote(models.Model):
    #quote = models.Manager()
    quote_text = models.CharField(max_length=1000)
    #quote_by = tähän

    def __str__(self):
        return self.quote_text


    def quote_by(self):
        pass


class Vote(models.Model):
    quote = models.ForeignKey(Quote, on_delete=models.CASCADE)
    vote_text = models.CharField(max_length=200)
    votes = models.IntegerField(default=0)

    def __str__(self):
        return self.vote_text

