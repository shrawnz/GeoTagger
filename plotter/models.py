from __future__ import unicode_literals

from django.db import models
import os

# Create your models here.
def get_image_path(instance, filename):
    return os.path.join('photos', str(instance.id), filename)

class Image(models.Model):
	name = models.CharField(max_length=50)
	image = models.ImageField(upload_to=get_image_path, blank=True, null=True)