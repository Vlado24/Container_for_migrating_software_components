# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-05-16 00:54
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('file', '0002_auto_20180516_0047'),
    ]

    operations = [
        migrations.RenameModel(
            old_name='FileUpload',
            new_name='FileInput',
        ),
    ]
