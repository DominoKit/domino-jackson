![GWT3/J2CL compatible](https://img.shields.io/badge/GWT3/J2CL-compatible-brightgreen.svg) [![Build Status](https://travis-ci.org/DominoKit/domino-jackson.svg?branch=master)](https://travis-ci.org/DominoKit/domino-jackson)
<a title="Gitter" href="https://gitter.im/domino-gwt/Gwt-jackson-apt"><img src="https://badges.gitter.im/Join%20Chat.svg"></a>

gwt-jackson-apt
=====
jackson-apt is an APT bases JSON parser for [GWT](http://www.gwtproject.org/) based on [gwt-jackson](https://github.com/nmorel/gwt-jackson) the original thanks goes to [Nicolas Morel](https://github.com/nmorel) for building gwt-jackson library.

this library provides limited support to what gwt-jackson provides, but this will change as more support for features will be added gradually.

unlike the gwt-jackson, jackson-apt allow the use of the same Mapper class on both client side and server side, which means that the mappers can be defined and generated into the shared module instead of the client module, it is even possible to use the mapper in a pure server side application e.g: Android Apps.
 

Quick start
-------------

### Maven dependencies

*currently this is only available as a snapshot on repository* :

```xml
<repository>
 <id>sonatype-snapshots-repo</id>
 <url>https://oss.sonatype.org/content/repositories/snapshots</url>
 <snapshots>
    <enabled>true</enabled>
    <updatePolicy>always</updatePolicy>
    <checksumPolicy>fail</checksumPolicy>
 </snapshots>
</repository>
```

Add the following dependencies to your project pom file

#### Dependencies

```xml
<dependency>
    <groupId>org.dominokit.jackson</groupId>
    <artifactId>jackson-apt</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>org.dominokit.jackson</groupId>
    <artifactId>jackson-apt-processor</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

To use the lib with GWT you will also need to inherit the module:

`<inherits name="org.dominokit.jacksonapt.GwtJacksonApt"/>`

> With GWT project Mappers generated in the client or server or shared can be used in the client side.


### Usage

Then just create an interface extending `ObjectReader`, `ObjectWriter` or `ObjectMapper` and annotate it with `JSONReader`, `JSONWriter` or `JSONMapper` respectively if you want to read JSON, write an object to JSON or both.

Here's an example without annotation :

```java
public class TestEntryPoint implements EntryPoint {

    @JSONMapper
    public static interface PersonMapper extends ObjectMapper<Person> {
        PersonMapper INSTANCE= new TestEntryPoint_PersonMapperImpl();
    }

    public static class Person {

        private String firstName;
        private String lastName;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    @Override
    public void onModuleLoad() {

        String json = PersonMapper.INSTANCE.write( new Person( "John", "Doe" ) );
        GWT.log( json ); // > {"firstName":"John","lastName":"Doe"}

        Person person = PersonMapper.INSTANCE.read( json );
        GWT.log( person.getFirstName() + " " + person.getLastName() ); // > John Doe
    }
}
```

if you have a data object in your shared module and want to generate a mapper that can be used for both client and server you can annotate the data object class, or create the interface as the sample above:

```java
@JSONMapper
public class GreetingResponse{

	public static GreetingResponse_MapperImpl MAPPER=new GreetingResponse_MapperImpl();

	private String greeting;
	private String serverInfo;
	private String userAgent;

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public String getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(String serverInfo) {
		this.serverInfo = serverInfo;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
```

### Supported types

All types/mixed types in below sample class are supported

```java

public class TestBean {

    //primitives and boxed
    public String stringField;
    public byte byteField;
    public Byte boxedByteField;
    public short shortField;
    public Short boxedShortField;
    public int intField;
    public Integer boxedIntField;
    public long longField;
    public Long boxedLongField;
    public double doubleField;
    public Double boxedDoubleField;
    public float floatField;
    public Float boxedFloatField;
    public boolean booleanField;
    public Boolean boxedBooleanField;
    public char charField;
    public Character boxedCharField;
    
    //special types
    public BigInteger bigIntegerField;
    public BigDecimal bigDecimalField;
    public Date dateField;
    public java.sql.Date sqlDateField;
    public Time timeField;
    public Timestamp timestampField;
    public Void voidField;

    //Enums
    public AnEnum enumField;

    //1 dimensional primitives arrays
    public byte[] byteFieldArray;
    public short[] shortFieldArray;
    public int[] intFieldArray;
    public long[] longFieldArray;
    public double[] doubleFieldArray;
    public float[] floatFieldArray;
    public boolean[] booleanFieldArray;
    public char[] charFieldArray;

    //2 dimensional primitives arrays
    public byte[][] byteFieldArray2d;
    public short[][] shortFieldArray2d;
    public int[][] intFieldArray2d;
    public long[][] longFieldArray2d;
    public double[][] doubleFieldArray2d;
    public float[][] floatFieldArray2d;
    public boolean[][] booleanFieldArray2d;
    public char[][] charFieldArray2d;

    //1 dimensional Boxed arrays
    public String[] stringFieldArray;
    public Byte[] boxedByteFieldArray;
    public Short[] boxedShortFieldArray;
    public Integer[] boxedIntFieldArray;
    public Long[] boxedLongFieldArray;
    public Double[] boxedDoubleFieldArray;
    public Float[] boxedFloatFieldArray;
    public Boolean[] boxedBooleanFieldArray;
    public Character[] boxedCharFieldArray;
    
    //1 dimensional special types arrays
    public BigInteger[] bigIntegerFieldArray;
    public BigDecimal[] bigDecimalFieldArray;
    public Date[] dateFieldArray;
    public java.sql.Date[] sqlDateFieldArray;
    public Time[] timeFieldArray;
    public Timestamp[] timestampFieldArray;
    public Void[] voidFieldArray;

    //2 dimensional boxed arrays
    public String[][] stringFieldArray2d;
    public Byte[][] boxedByteFieldArray2d;
    public Short[][] boxedShortFieldArray2d;
    public Integer[][] boxedIntFieldArray2d;
    public Long[][] boxedLongFieldArray2d;
    public Double[][] boxedDoubleFieldArray2d;
    public Float[][] boxedFloatFieldArray2d;
    public Boolean[][] boxedBooleanFieldArray2d;
    public Character[][] boxedCharFieldArray2d;
    
    //2 dimensional special types arrays
    public BigInteger[][] bigIntegerFieldArray2d;
    public BigDecimal[][] bigDecimalFieldArray2d;
    public Date[][] dateFieldArray2d;
    public java.sql.Date[][] sqlDateFieldArray2d;
    public Time[][] timeFieldArray2d;
    public Timestamp[][] timestampFieldArray2d;
    public Void[][] voidFieldArray2d;

    //1 dimensional enum arrays
    public AnEnum[] enumArray;
    //2 dimensional enum arrays
    public AnEnum[][] enumArray2d;

    //collections
    public AbstractCollection<String> abstractCollection;
    public AbstractList<String> abstractList;
    public AbstractQueue<String> abstractQueue;
    public AbstractSequentialList<String> abstractSequentialList;
    public AbstractSet<String> abstractSet;
    public ArrayList<String> arrayList;
    public Collection<String> collection;
    public EnumSet<AnEnum> enumSet;
    public HashSet<String> hashSet;
    public Iterable<String> iterable;
    public LinkedHashSet<String> linkedHashSet;
    public LinkedList<String> linkedList;
    public List<String> list;
    public PriorityQueue<String> priorityQueue;
    public Queue<String> queue;
    public Set<String> set;
    public SortedSet<String> sortedSet;
    public Stack<String> stack;
    public TreeSet<String> treeSet;
    public Vector<String> vector;
    
    //Maps
    public AbstractMap<String, String> abstractMap;
    public EnumMap<AnEnum, Integer> enumMap;
    public HashMap<Integer, Double> hashMap;
    public IdentityHashMap<Long, Date> identityHashMap;
    public LinkedHashMap<Double, AnEnum> linkedHashMap;
    public Map<Short, Time> map;
    public SortedMap<String, Short> sortedMap;
    public TreeMap<String, BigInteger> treeMap;
    
    public AnotherBean anotherBean;
}

```

### Road map

More features from the original GWT-Jackson will be supported, starting from the most basic and important ones
- [x] Remove all JSNI.
- [x] Support nested beans.
- [x] Support JsTypes. _Partially done_
- [ ] Support [Jackson annotations](https://github.com/nmorel/gwt-jackson/wiki/Jackson-annotations-support)
    
    - [x] Support for JsonIgnore
    - [x] Support for JsonProperty _Only custom property name is supported_
    - [x] Support for JsonIgnoreProperties(ignoreUnknow = true)
    
- [x] Support Inheritance -Mapper can serialize extended classes properties but does not dedict subtypes from json string_
- [ ] Support [custom serializers/deserializers](https://github.com/nmorel/gwt-jackson/wiki/Custom-serializers-and-deserializers)
- [ ] Support Guava extension.
- [ ] Support [Mix-in annotations](https://github.com/nmorel/gwt-jackson/wiki/Mix-in-annotations)
 



