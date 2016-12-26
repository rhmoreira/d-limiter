# d-limiter
Easy to use, standalone and lightweight framework to parse and format flat-file contents to Java POJO's.

Following java beans convention for attribute names and getters and setters methods, does back and forth POJO transformation from flat-files, based on delimiters (CSV) or positional values (FFP). See example as follows: 

## TOKENIZED EXAMPLE
### TokenizedPerson.java (POJO)
``` java
  @Delimited(DelimitationType.TOKEN)
  public class TokenizedPerson{

    @Index(0)
    private String name;

    @Index(1)
    private Integer age;

    @Index(2)
    private TokenizedAddress address;

    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public Integer getAge() {
      return age;
    }
    public void setAge(Integer age) {
      this.age = age;
    }
    public TokenizedAddress getAddress() {
      return address;
    }
    public void setAddress(TokenizedAddress address) {
      this.address = address;
    }
  }

```
### TokenizedAddress.java (POJO)
``` java
@Delimited(DelimitationType.TOKEN)
public class TokenizedAddress {

	@Index(0)
	private String street;
	
	@Index(1)
	private Integer number;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
```
### Run TOKENIZED EXAMPLE
``` java
public class Main {

	public static void main(String[] args) throws Exception{
		//Create a configuration with a specific token delimiter for the TokenizedAddress entity.
		Configuration addressConf = new Configuration();
		addressConf.setDelimiterToken("_");
		
		//Register the entity with its own configuration
		DelimiterScanner.scan(TokenizedAddress.class, addressConf);
		
		//Obtain the delimiter implementation given the annotated POJO class
		Delimiter<TokenizedPerson> delimiter = Delimiters.getInstance(TokenizedPerson.class);
		
		//Parse the String contents to a TokenizedPerson instance
		TokenizedPerson person = delimiter.parse("John Doe;30;Who knows St._0");
		
		//See the result!
		System.out.println(person);
		
		//Format the person back to its original flat-format form
		String flatFormat = delimiter.format(person);
		
		//See the result!
		System.out.println(flatFormat);
	}
}
```

## POSITIONAL EXAMPLE
### PositionalPerson.java (POJO)

``` java
@Delimited(DelimitationType.POSITIONAL)
public class PositionalPerson {

	@Position(start=1, end=30)
	private String name;
	
	@Position(start=31, end=40)
	private Integer age;
	
	@Position(start=41, end=70)
	private PositionalAddress address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public PositionalAddress getAddress() {
		return address;
	}

	public void setAddress(PositionalAddress address) {
		this.address = address;
	}
}

```
### PositionalAddress.java (POJO)
``` java
@Delimited(DelimitationType.POSITIONAL)
public class PositionalAddress {

	@Position(start=1, end=25)
	private String street;
	
	@Position(start=26, end=30, orientation=@Orientation(padding=OrientationPadding.LEFT))
	private String number;

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}
```

### Run POSITIONAL EXAMPLE
``` java
public class Main {

	public static void main(String[] args) throws Exception{
		//Obtain the delimiter implementation given the annotated POJO class
		Delimiter<PositionalPerson> delimiter = Delimiters.getInstance(PositionalPerson.class);
		
		//Parse the String contents to a PositionalPerson instance
		PositionalPerson person = delimiter.parse("John Doe Seven                34        Insanity St.               100");
		
		//See the result!
		System.out.println(person);
		
		//Format the person back to its original flat-format form
		String flatFormat = delimiter.format(person);
		
		//See the result!
		System.out.println(flatFormat);
	}
}
```
