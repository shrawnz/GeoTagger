from django.contrib import admin

# Register your models here.
from .models import Image

admin.site.register(Image)

class ImageInline(admin.StackedInline):
	model = Image

class GalleryAdmin(admin.ModelAdmin):
    inlines = [ ImageInline, ]