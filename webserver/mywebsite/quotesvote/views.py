from django.shortcuts import get_object_or_404, render
from django.http import HttpResponse, HttpResponseRedirect
from django.urls import reverse
from django.http import Http404
from .models import Quote, Vote


def index(request):
    latest_quote_list = Quote.objects.all()
    context = {'latest_quote_list': latest_quote_list}
    return render(request, 'quotesvote/index.html', context)


def quote(request, quote_id):
    quote = get_object_or_404(Quote, pk=quote_id)
    return render(request, 'quotesvote/quote.html', {'quote': quote})


def results(request, quote_id):
    quote = get_object_or_404(Quote, pk=quote_id)
    return render(request, 'quotesvote/results.html', {'quote': quote})



def vote(request, quote_id):
    quote = get_object_or_404(Quote, pk=quote_id)
    try:
        selected_choice = quote.vote_set.get(pk=request.POST['vote'])
    except (KeyError, Vote.DoesNotExist):
        # Redisplay the quote voting form.
        return render(request, 'quotesvote/quote.html', {
            'quote': quote,
            'error_message': "You didn't select a choice.",
        })
    else:
        selected_choice.votes += 1
        selected_choice.save()
        # Always return an HttpResponseRedirect after successfully dealing
        # with POST data. This prevents data from being posted twice if a
        # user hits the Back button.
        return HttpResponseRedirect(reverse('quotesvote:results', args=(quote.id,)))

