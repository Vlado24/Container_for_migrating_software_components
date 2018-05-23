from django.db import models
from datetime import datetime
from django.dispatch import receiver
from django.db.models.signals import post_save
from fcm_django.models import FCMDevice


def directory_path(instance, filename):
    return '{0}/{1}'.format('files', filename)


def directory_response_path(instance, filename):
    return '{0}/{1}'.format('files', filename)


class FileInput(models.Model):
    created = models.DateTimeField(auto_now_add=True)
    datafile = models.FileField(upload_to=directory_path, unique=True)
    loops = models.IntegerField(blank=True, null=True)

    def filename(self):
        return self.datafile.url

    def __str__(self):
        return self.datafile.name


class FileResponse(models.Model):
    created = models.DateTimeField(auto_now_add=True)
    datafile = models.FileField(upload_to=directory_response_path)
    duration = models.BigIntegerField(blank=True, null=True)
    time = models.DateTimeField(auto_now_add=True)
    file_input = models.ForeignKey(FileInput)
    device_id = models.ForeignKey(FCMDevice)

    def __str__(self):
        return self.datafile.name




@receiver(post_save, sender=FileInput)
def file_input_post_save(sender, instance, **kwargs):
    devices = FCMDevice.objects.all()
    devices.send_message(data={"filename": instance.filename(), "loops": instance.loops})
