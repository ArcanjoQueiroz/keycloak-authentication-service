FROM python:3.9.4-slim
EXPOSE 9097
RUN apt-get update && apt-get install libzbar0 -y
COPY . /root
COPY ./requirements.txt /root
RUN python3.9 -m pip install --upgrade pip
RUN pip3 install -r /root/requirements.txt
WORKDIR /root
CMD ["python3.9", "app.py"]
