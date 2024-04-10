# AscanAPI
A application for paper plugins.

## Authors
- Radik Friedrichs [[@flxwdns](https://www.github.com/flxwdns)]

## Dependency
Gradle, Maven, SBT, Leiningen: `https://jitpack.io/#FlxwDNS/AscanAPI`

## FAQ
#### I got following error: `java.lang.IllegalArgumentException: Plugin cannot be null`
Go into your plugin instance and add following code: `AscanAPI.init(this, new InventoryConfig());`

## Roadmap
- Empty

## Support
For support, @flxwdns [Discord].