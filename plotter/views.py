from django.shortcuts import render
from django.http import HttpResponse
from django.core.files.storage import FileSystemStorage
from django.conf import settings
from PIL import Image
from PIL.ExifTags import TAGS, GPSTAGS
import os, json

arr = []
coordinates = []

def home(request):
    print "yoo"
    return render(request, 'index.html', {'what':'Django File Upload'})


def index(request):
    for x in request.FILES.getlist('file'):
    	print x.name
        def process(f):
            with open('/home/shreyans/django/maps/media/' + x.name, 'wb+') as destination:
                print destination
                for chunk in f.chunks():
                    destination.write(chunk)
        process(x)
    coordinates = exif()
    fup = 1
    return render(request, 'index.html', { 'coordinates' : coordinates } )

def exif():
	path = '/home/shreyans/django/maps/media/'
	for filename in os.listdir(path):
		imgpath = path + filename
		image = Image.open(imgpath)
		exif_data = get_exif_data(image)
 		d = get_lat_lon(exif_data)
		arr.append(d[0])
		arr.append(d[1])
 	return arr


# def index(request):
# 	print "woohoo"
# 	if request.method == 'POST':
# 		for filename,file in request.FILES.iteritems():
# 			fs = FileSystemStorage()
# 			filename = fs.save(filename, file)
# 			uploaded_file_url = fs.url(filename)
# 		path='/home/shreyans/django/maps/media/exif.jpg'
# 		image = Image.open(path)
# 		exif_data = get_exif_data(image)
# 		arr = get_lat_lon(exif_data)
# 		lat = arr[0]
# 		longi = arr[1]
# 		# gps = []
# 		# latlong = []

# 		# for i in arr:
# 		# 	latlong.append(i[0])
# 		# 	latlong.append(i[1])
# 		# 	gps.append(latlong)
# 		# 	latlong=[]
	# 	return render(request, 'index.html', locals())
	
	# return render(request, 'index.html')


def get_exif_data(image):
    """Returns a dictionary from the exif data of an PIL Image item. Also converts the GPS Tags"""
    exif_data = {}
    info = image._getexif()
    if info:
        for tag, value in info.items():
            decoded = TAGS.get(tag, tag)
            if decoded == "GPSInfo":
                gps_data = {}
                for t in value:
                    sub_decoded = GPSTAGS.get(t, t)
                    gps_data[sub_decoded] = value[t]

                exif_data[decoded] = gps_data
            else:
                exif_data[decoded] = value

    return exif_data

def _get_if_exist(data, key):
    if key in data:
        return data[key]
		
    return None
	
def _convert_to_degress(value):
    """Helper function to convert the GPS coordinates stored in the EXIF to degress in float format"""
    d0 = value[0][0]
    d1 = value[0][1]
    d = float(d0) / float(d1)

    m0 = value[1][0]
    m1 = value[1][1]
    m = float(m0) / float(m1)

    s0 = value[2][0]
    s1 = value[2][1]
    s = float(s0) / float(s1)

    return d + (m / 60.0) + (s / 3600.0)

def get_lat_lon(exif_data):
    """Returns the latitude and longitude, if available, from the provided exif_data (obtained through get_exif_data above)"""
    lat = None
    lon = None

    if "GPSInfo" in exif_data:		
        gps_info = exif_data["GPSInfo"]

        gps_latitude = _get_if_exist(gps_info, "GPSLatitude")
        gps_latitude_ref = _get_if_exist(gps_info, 'GPSLatitudeRef')
        gps_longitude = _get_if_exist(gps_info, 'GPSLongitude')
        gps_longitude_ref = _get_if_exist(gps_info, 'GPSLongitudeRef')

        if gps_latitude and gps_latitude_ref and gps_longitude and gps_longitude_ref:
            lat = _convert_to_degress(gps_latitude)
            if gps_latitude_ref != "N":                     
                lat = 0 - lat

            lon = _convert_to_degress(gps_longitude)
            if gps_longitude_ref != "E":
                lon = 0 - lon

    return lat, lon

