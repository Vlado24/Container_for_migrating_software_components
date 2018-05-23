
from rest_framework import serializers
from file.models import FileInput, FileResponse
from fcm_django.models import FCMDevice


class FileInputSerializer(serializers.HyperlinkedModelSerializer):
    send_all_device = serializers.HyperlinkedIdentityField(lookup_field='pk', view_name='api:send-all-device', read_only=True)

    class Meta:
        model = FileInput
        fields = ('id', 'created', 'datafile', 'loops', 'send_all_device')


class FileResponseSerializer(serializers.HyperlinkedModelSerializer):
    device_id = serializers.SlugRelatedField(slug_field='registration_id', queryset=FCMDevice.objects.all())
    file_input = serializers.SlugRelatedField(slug_field='datafile', write_only=True, queryset=FileInput.objects.all())
    file_input_detail = serializers.HyperlinkedRelatedField(source='file_input', view_name='api:file-input-detail', read_only=True)
    file_input_detail_name = serializers.CharField(source='file_input.datafile', read_only=True)

    class Meta:
        model = FileResponse
        fields = ('id', 'created', 'datafile', 'duration', 'time', 'device_id', 'file_input', 'file_input_detail', 'file_input_detail_name')


class GetLastResultSerializer(serializers.Serializer):
    input_filename = serializers.CharField()
