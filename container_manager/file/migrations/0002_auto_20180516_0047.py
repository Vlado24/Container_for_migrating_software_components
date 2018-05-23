# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-05-16 00:47
from __future__ import unicode_literals

from django.db import migrations, models
import file.models


class Migration(migrations.Migration):

    dependencies = [
        ('file', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='FileResponse',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('created', models.DateTimeField(auto_now_add=True)),
                ('datafile', models.FileField(upload_to=file.models.directory_response_path)),
            ],
        ),
        migrations.AlterField(
            model_name='fileupload',
            name='datafile',
            field=models.FileField(upload_to=file.models.directory_path),
        ),
    ]