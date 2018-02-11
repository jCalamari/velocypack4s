### Velocypack for Scala

A Scala wrapper for [Java VelocyPack](https://github.com/arangodb/java-velocypack). 
[![Build Status](https://travis-ci.org/jCalamari/velocypack-module-scala.svg?branch=master)](https://travis-ci.org/jCalamari/velocypack-module-scala)[![codecov.io](http://codecov.io/github/jCalamari/velocypack4s/coverage.svg?branch=master)](http://codecov.io/github/jCalamari/velocypack4s?branch=master)

The objective is to allow seamless use with Scala case classes without the need to write boilerplate and error-prone Velocypack serializer/deserializer conversions yourself.

#### Modules

The library is composed by these modules:

    - core: encapsulates common behaviours and provides basic encoders/decoders
    - shapeless: case class encoder/decoder generation using shapeless 