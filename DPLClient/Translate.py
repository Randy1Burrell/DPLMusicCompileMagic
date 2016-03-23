#!/bin/bash
from gtk import TextBlob

def Translator(Text, To="en"):
	blob = TextBlob(text)
	file = open("Song.txt", "w" )
	file.write(blob.translate(to=To))