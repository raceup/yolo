#!/usr/bin/env bash

# Releases software to main channel (Race UP ED dropbox)

# output folder
OUTPUT_FOLDER=${HOME}"/Dropbox/Elettronica & Controllo 2018/07 - Firmware/Telemetria/yolo/"

# inputs
CMD_INPUT="build/cmd/yolo.jar"
GUI_INPUT="build/gui/yolo.jar"

# outputs
CMD_OUTPUT=${OUTPUT_FOLDER}cmd.jar
GUI_OUTPUT=${OUTPUT_FOLDER}gui.jar

# clean
rm "${CMD_OUTPUT}"
rm "${GUI_OUTPUT}"

# copy
cp "${CMD_INPUT}" "${CMD_OUTPUT}"
cp "${GUI_INPUT}" "${GUI_OUTPUT}"
