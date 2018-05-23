"""container_manager URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url, include
from django.contrib import admin
from rest_framework_swagger.views import get_swagger_view
from rest_framework.routers import DefaultRouter
from fcm_django.api.rest_framework import FCMDeviceViewSet
from file import views as file_v


schema_view = get_swagger_view(title='API Dokumentácia')

router = DefaultRouter()
router.register(r'device', FCMDeviceViewSet, base_name='device')
router.register(r'file-input', file_v.FileInputViewSet, base_name='file-input')
router.register(r'file-response', file_v.FileResponseViewSet, base_name='file-response')


urlpatterns = router.urls + [
    url(r'^send-all-device/(?P<pk>\d+)/$', file_v.SendAllDevice.as_view(), name='send-all-device'),
    url(r'^get-last-result/$', file_v.GetLastResult.as_view(), name='get-last-result'),
]
