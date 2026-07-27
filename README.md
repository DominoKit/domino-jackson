![logoimage](https://raw.githubusercontent.com/DominoKit/DominoKit.github.io/master/logo/128.png)

<a title="Gitter" href="https://gitter.im/DominoKit/domino"><img src="https://badges.gitter.im/Join%20Chat.svg"></a>
[![Development Build Status](https://github.com/DominoKit/domino-jackson/actions/workflows/deploy.yaml/badge.svg?branch=development)](https://github.com/DominoKit/domino-jackson/actions/workflows/deploy.yaml/badge.svg?branch=development)
![Maven Central](https://img.shields.io/badge/Release-1.0.0--RC3-green)
![Sonatype Nexus (Snapshots)](https://img.shields.io/badge/Snapshot-HEAD--SNAPSHOT-orange)
![GWT3/J2CL compatible](https://img.shields.io/badge/GWT3/J2CL-compatible-brightgreen.svg)

# Domino-jackson

[Domino-jackson](https://dominokit.com/solutions/domino-jackson/v1) is an annotation-processor-based JSON mapper that works on both the client side ([GWT](http://www.gwtproject.org/) and [J2CL](https://github.com/google/j2cl)) and the JVM. It is based on [gwt-jackson](https://github.com/nmorel/gwt-jackson); thanks to [Nicolas Morel](https://github.com/nmorel) for the original work.

The same generated mappers can be used on the server and in the browser, enabling shared DTOs, easier testing, and consistent JSON behavior across environments.

## Contents

- [Overview](#overview)
- [Modules](#modules)
- [Requirements](#requirements)
- [Install](#install)
- [Quick example](#quick-example)
- [Annotations](#annotations)
- [Registry generation](#registry-generation)
- [Custom serializers and deserializers](#custom-serializers-and-deserializers)
- [Runtime contexts](#runtime-contexts)
- [Build and test](#build-and-test)
- [Documentation](#documentation)
- [License](#license)

## Overview

Domino-jackson generates JSON mappers, readers, and writers at compile time. It avoids runtime reflection (important for GWT/J2CL) and provides a small runtime API that works the same on JVM and browser targets.

Core capabilities include:

- Compile-time generation of `ObjectMapper`, `ObjectReader`, and `ObjectWriter` implementations.
- Optional registry generation for type-safe mapper lookups.
- Custom serializers/deserializers via annotations.
- Consistent behavior across JVM, GWT, and J2CL.

## Modules

- `domino-jackson`: runtime library, JSON reader/writer APIs, and GWT module resources.
- `domino-jackson-processor`: annotation processor that generates mapper implementations and registries.

## Requirements

- Java 11+ for builds (the parent POM sets `maven.compiler.release` to 11).
- Maven 3.6.0+ (matches the project documentation).
- A build that runs annotation processing (Maven or Gradle).
- For GWT/J2CL usage, include the GWT module `org.dominokit.jackson.Jackson` (see `domino-jackson/src/main/module.gwt.xml`).

## Install

Add the runtime dependency and the processor. Replace `VERSION` with a release or snapshot:

```xml
<properties>
  <domino.jackson.version>VERSION</domino.jackson.version>
</properties>

<dependencies>
  <dependency>
    <groupId>org.dominokit</groupId>
    <artifactId>domino-jackson</artifactId>
    <version>${domino.jackson.version}</version>
  </dependency>
  <dependency>
    <groupId>org.dominokit</groupId>
    <artifactId>domino-jackson-processor</artifactId>
    <version>${domino.jackson.version}</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

For Maven annotation processing (recommended):

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-compiler-plugin</artifactId>
  <configuration>
    <annotationProcessorPaths>
      <path>
        <groupId>org.dominokit</groupId>
        <artifactId>domino-jackson-processor</artifactId>
        <version>${domino.jackson.version}</version>
      </path>
    </annotationProcessorPaths>
  </configuration>
</plugin>
```

If you use GWT, add the module inherit:

```xml
<inherits name="org.dominokit.jackson.Jackson"/>
```

## Quick example

Define a POJO and a mapper interface, then use the generated implementation:

```java
public class Person {
  public String firstName;
  public String lastName;
}

@JSONMapper
public interface PersonMapper extends ObjectMapper<Person> {}

PersonMapper mapper = PersonMapperImpl.INSTANCE;
Person person = mapper.read("{\"firstName\":\"Ada\",\"lastName\":\"Lovelace\"}");
String json = mapper.write(person);
```

Alternatively, annotate the POJO directly:

```java
@JSONMapper
public class Person {
  public String firstName;
  public String lastName;
}

Person person = new Person();
person.firstName = "Ada";
person.lastName = "Lovelace";

String json = Person_MapperImpl.INSTANCE.write(person);
```

The processor generates `PersonMapperImpl` (for interface-based mappers) or `Person_MapperImpl` (for POJO-based mappers), each with a public `INSTANCE` singleton.

## Annotations

- `@JSONMapper` on a POJO or on an interface that extends `ObjectMapper<T>` generates a mapper.
- `@JSONReader` on an interface that extends `ObjectReader<T>` generates a reader-only mapper.
- `@JSONWriter` on an interface that extends `ObjectWriter<T>` generates a writer-only mapper.
- `@JSONRegistration("Prefix")` on a package generates a registry named `PrefixJsonRegistry`.

## Supported Jackson annotations

Domino-jackson supports the following Jackson annotations (with noted limitations):

- `@JsonIgnoreProperties(ignoreUnknown = true)`
- `@JsonIgnore`
- `@JsonProperty`
- `@JsonFormat`
- `@JsonInclude`
- `@JsonDeserialize(builder = SomeBuilder.class)`
- `@JsonPOJOBuilder(buildMethodName = "create")`
- `@JsonIdentityInfo` (limited: `@JsonIdentityReference` is not supported)
- `@JsonTypeInfo` (partial support; see Polymorphism)
- `@JsonSubTypes`

## Supported data types

Domino-jackson supports any composition of the following:

- Primitives and boxed types, plus `String`.
- Special types: `BigInteger`, `BigDecimal`, `Date`, `java.sql.Date`, `Time`, `Timestamp`, `Void`.
- Enums.
- 1D and 2D arrays of primitives, boxed types, and special types.
- Collections: `List`, `Set`, `Queue`, `Stack`, `Vector`, `SortedSet`, `Abstract*` collections, and common concrete types.
- Maps: `Map`, `SortedMap`, `EnumMap`, `HashMap`, `TreeMap`, `LinkedHashMap`, `IdentityHashMap`, and other common variants.
- Nested POJOs (beans) composed of the above.

## Registry generation

Use `@JSONRegistration` to generate a `JsonRegistry` that maps `TypeToken` to mappers/readers/writers:

```java
@JSONRegistration("App")
package com.example.app;

import org.dominokit.jackson.annotation.JSONRegistration;
```

```java
JsonRegistry registry = AppJsonRegistry.getInstance();
ObjectMapper<Person> mapper = registry.getMapper(TypeToken.of(Person.class));
```

For parameterized types in GWT/J2CL, use nested `TypeToken` values:

```java
TypeToken<List<Map<Integer, Person>>> type =
    new TypeToken<List<Map<Integer, Person>>>(
        List.class,
        new TypeToken<Map<Integer, Person>>(
            Map.class,
            TypeToken.of(Integer.class),
            TypeToken.of(Person.class)) {}) {};
```

## Generics

The processor can handle generic types when concrete type arguments are available at compile time. A common pattern is:

- Define a generic base class.
- Annotate the concrete subclass with `@JSONMapper`.

Annotating the generic base type alone does not provide enough type information for code generation. Avoid mapper definitions that use unbound type variables (e.g., `ObjectMapper<T>`).

## Custom serializers and deserializers

Provide custom implementations by extending `JsonSerializer<T>` or `JsonDeserializer<T>` and annotating them:

```java
@CustomSerializer(Employee.class)
public class EmployeeSerializer extends JsonSerializer<Employee> {
  @Override
  protected void doSerialize(JsonWriter writer, Employee value, JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    writer.value(value.getId() + "," + value.getName());
  }
}
```

```java
@CustomDeserializer(Employee.class)
public class EmployeeDeserializer extends JsonDeserializer<Employee> {
  @Override
  protected Employee doDeserialize(JsonReader reader, JsonDeserializationContext ctx,
      JsonDeserializerParameters params) {
    String[] parts = reader.nextString().split(",");
    Employee employee = new Employee();
    employee.setId(Long.parseLong(parts[0]));
    employee.setName(parts[1]);
    return employee;
  }
}
```

If your custom mappers live in a different module or external JAR, you can register them via `CustomMappersLoader` and Java service loading:

```java
@AutoService(CustomMappersLoader.class)
public class PersonCustomMappersLoader implements CustomMappersLoader {
  @Override
  public void register(RegistryWrapper registry) {
    registry.registerSerializer(
        Person.class.getCanonicalName(), PersonCustomBeanSerializer.class);
    registry.registerDeserializer(
        Person.class.getCanonicalName(), PersonCustomBeanDeserializer.class);
  }
}
```

## Inheritance

Fields declared in superclasses are included during serialization/deserialization. The inheritance tree can be as deep as needed as long as concrete types are resolvable by the processor.

## Polymorphism

Domino-jackson supports polymorphic hierarchies using `@JsonTypeInfo` and `@JsonSubTypes` with these limitations:

- Only `@JsonTypeInfo(use = Id.NAME)` is supported.
- Base and subtypes must not be generic types.
- Unbounded wildcards are not supported.
- Mapper definitions cannot use wildcards or type variables in the mapped type.
- Bounded wildcards are supported inside generic collections when bounds are part of the `@JsonTypeInfo`/`@JsonSubTypes` contract.
- Bounded wildcards are not supported for member fields of custom generic classes.

## Runtime contexts

`JacksonContextProvider` selects an implementation appropriate for the runtime:

- `ServerJacksonContext` for JVM environments.
- `JsJacksonContext` for browser environments (GWT/J2CL), using native `JSON.stringify`.

You can pass `JsonSerializationContext` and `JsonDeserializationContext` to control per-call options.

## Build and test

Typical commands:

- `mvn test` to run the default unit tests.
- `mvn -pl domino-jackson -am test` to focus on runtime tests.
- `mvn -pl domino-jackson-processor -am test` to focus on annotation processor tests.
- `mvn spotless:check` to verify Java formatting.
- `mvn spotless:apply` to rewrite sources using the configured formatter.

There are additional profiles such as `java17-tests` (adds `src/it/java`) and `java23-processors`
for newer JDK-specific processor behavior.

## Documentation

Checkout the [Quick start guide](https://dominokit.com/solutions/domino-jackson/v1/docs/getting-started/quick-start)
and visit our [documentation](https://dominokit.com/solutions/domino-jackson/v1/docs/getting-started) for more details.

## License

Apache License 2.0. See `LICENSE`.
 
