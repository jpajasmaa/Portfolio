from django.urls import path

from . import views


app_name = 'quotesvote'
urlpatterns = [
    # ex: /polls/
    path('', views.index, name='index'),
    # ex: /polls/5/
    path('<int:quote_id>/', views.quote, name='quote'),
    # ex: /polls/5/results/
    path('<int:quote_id>/results/', views.results, name='results'),
    # ex: /polls/5/vote/
    path('<int:quote_id>/vote/', views.vote, name='vote'),
]