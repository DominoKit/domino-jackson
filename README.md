
[![Build Status](https://travis-ci.org/vegegoku/gwt-jackson-apt.svg?branch=master)](https://travis-ci.org/vegegoku/gwt-jackson-apt)

gwt-jackson-apt
=====
gwt-jackson-apt is an APT bases JSON parser for [GWT](http://www.gwtproject.org/) based on [gwt-jackson](https://github.com/nmorel/gwt-jackson) the original thanks goes to [Nicolas Morel](https://github.com/nmorel) for building gwt-jackson library.

this library provides limited support to what gwt-jackson provides, but this will change as more support for features will be added gradually.

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

Add the following dependecies to your project pom file

```xml
<dependency>
    <groupId>com.progressoft.brix.domino.gwtjackson</groupId>
    <artifactId>gwt-jackson-apt-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.progressoft.brix.domino.gwtjackson</groupId>
    <artifactId>gwt-jackson-apt-processor</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>

```

### Usage

Add `<inherits name="com.progressoft.brix.domino.gwtjackson.GwtJacksonApt"/>` to your module descriptor XML file.

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
}

```

### Road map

More features from the original GWT-Jackson will be supported, starting from the most basic and important ones
- Remove all JSNI, _JSNI is partially removed but still used in the serializers and deserializers_ 
- ~~Support nested beans~~ _done_.
- Support JsTypes
- Support [Jackson annotations](https://github.com/nmorel/gwt-jackson/wiki/Jackson-annotations-support)
- Support Inheritance
- Support [custom serializers/deserializers](https://github.com/nmorel/gwt-jackson/wiki/Custom-serializers-and-deserializers)
- Support Guava extension.
- Support [Mix-in annotations](https://github.com/nmorel/gwt-jackson/wiki/Mix-in-annotations)
 



