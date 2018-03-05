### Velocypack for Scala

A Scala wrapper for [Java VelocyPack](https://github.com/arangodb/java-velocypack). 
[![Build Status](https://travis-ci.org/jCalamari/velocypack4s.svg?branch=master)](https://travis-ci.org/jCalamari/velocypack4s)[![codecov.io](http://codecov.io/github/jCalamari/velocypack4s/coverage.svg?branch=master)](http://codecov.io/github/jCalamari/velocypack4s?branch=master)

The objective is to allow seamless use with Scala case classes without the need to write boilerplate and error-prone Velocypack serializer/deserializer conversions yourself.

#### Modules

The library is composed by these modules:

    - core: encapsulates common behaviours and provides basic vpack formats
    - macros: case class format generation using Scala macros 

#### Usage

To use velocypack4s in your project add the following in your build.sbt file:

```
libraryDependencies += "org.scalamari" %% "velocypack4s-core" % "0.0.1"
```

Simple serialization/deserialization with velocypack4s:

```scala

import org.scalamari.velocypack4s.core._
import org.scalamari.velocypack4s.macros._
import com.arangodb.velocypack._

case class Foo(bar: String)

val fooSerializer: VPackSerializer[Foo] = serializer[Foo]

val fooDeserializer: VPackDeserializer[Foo] = deserializer[Foo]

object Module extends VPackModule {

  override def setup[C <: VPackSetupContext[C]](context: C): Unit = {
    context.registerSerializer(classOf[Foo], fooSerializer)

    context.registerDeserializer(classOf[Foo], fooDeserializer)
  }

}

```

#### About README

The content of the README file is generated from `modules/docs/src/main/tut/README.md`

To edit the README file:

1. Edit `modules/docs/src/main/tut/README.md`
2. Run `sbt tut`
3. Commit both files.

#### TODO

- backward/forward compatibility tests