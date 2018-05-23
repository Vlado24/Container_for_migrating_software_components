from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.parsers import FormParser, MultiPartParser
from rest_framework.viewsets import ModelViewSet
from file.models import FileInput, FileResponse
from file.serializers import FileInputSerializer, FileResponseSerializer, GetLastResultSerializer
from rest_framework.generics import GenericAPIView, ListAPIView
from rest_framework import serializers, status
from fcm_django.models import FCMDevice
from django.shortcuts import get_object_or_404


class FileInputViewSet(ModelViewSet):

    queryset = FileInput.objects.all()
    serializer_class = FileInputSerializer
    parser_classes = (MultiPartParser, FormParser)

    def perform_create(self, serializer):
        serializer.save(datafile=self.request.data.get('datafile'))


class FileResponseViewSet(ModelViewSet):

    queryset = FileResponse.objects.all()
    serializer_class = FileResponseSerializer
    parser_classes = (MultiPartParser, FormParser)

    def perform_create(self, serializer):
        serializer.save(datafile=self.request.data.get('datafile'))


class SendAllDevice(GenericAPIView):

    def post(self, request, format=None, pk=None):

        if pk is None:
            return Response({"detail": "pk can not be none"}, status=status.HTTP_400_BAD_REQUEST)

        file = get_object_or_404(FileInput, pk=pk)

        devices = FCMDevice.objects.all()
        devices.send_message(data={"filename": file.filename(), "loops": file.loops})
        return Response({"detail": "ok"})

    def get_serializer_class(self):
        return serializers.Serializer


class GetLastResult(GenericAPIView):
    serializer_class = GetLastResultSerializer

    def post(self, request, format=None):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)

        input_filename = serializer.data.get('input_filename')

        file = FileResponse.objects.filter(file_input__datafile=input_filename).order_by('created').last()
        if file is None:
            return Response({"detail": "none response of file {}".format(input_filename)}, status=status.HTTP_400_BAD_REQUEST)
        serialized_file = FileResponseSerializer(file, context={'request': request})

        return Response(serialized_file.data)
