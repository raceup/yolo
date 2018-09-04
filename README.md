<div align="center">
	<h1>YOLO</h1>
	<em>Telemetry for desktops (GUI + cmdline)</em></br>

<a href="https://github.com/raceup/yolo/pulls"><img src="https://badges.frapsoft.com/os/v1/open-source.svg?v=103"></a> <a href="https://github.com/raceup/yolo/issues"><img src="https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat"></a> <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"></a>
</div>

## Install

0. You need Java (>=7): go run [download it](https://www.java.com/en/download/).
0. Next you can compile [the sources](app) or just run the provided [.jar](build/)


## Usage
- launch the [cmdline program](build/cmd/yolo.jar) with `java -jar yolo.jar`
specifying:
```shell
usage: yolo
 -bitrate,--can-bitrate <arg>   Bitrate of CAN. Possible options are:
                                [10k, 50k, 62k, 83k, 100k, 125k, 250k,
                                500k, 1m]
 -ip,--ip-kvaser <arg>          IP of Blackbird
 -view,--view <arg>             Update screen with CAN messages, car
                                model. Possible options are  [can, car]
```
- double click the [gui program](build/gui/yolo.jar) and follow the
instructions


## Got questions?

If you have questions or general suggestions, don't hesitate to submit
a new [Github issue](https://github.com/raceup/yolo/issues/new).


## Contributing
[Fork](https://github.com/raceup/yolo/fork) | Patch | Push | [Pull request](https://github.com/raceup/yolo/pulls)


## Feedback
Suggestions and improvements [welcome](https://github.com/raceup/yolo/issues)!


## Authors

| [![sirfoga](https://avatars0.githubusercontent.com/u/14162628?s=128&v=4)](https://github.com/sirfoga "Follow @sirfoga on Github") |
|---|
| [Stefano Fogarollo](https://sirfoga.github.io) |


## License
[MIT License](https://opensource.org/licenses/MIT)
