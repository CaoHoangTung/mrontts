# STT Norm Java tool

This is the project used for Vin STT Text Normalization

## Installation

### Tested Environment:
- `Gradle 7.0.2`
- `openjdk version "1.8.0_282"`

### Installation
**1. Clone the repository**
```shell
git clone git@bitbucket.org:vinbdi-slp/stt-norm-java.git
```

**2. Build the repository**

In the main project directory, run
```shell
./gradlew build
```

A `lib.jar` file will be availabed at `lib/build/libs`

**3. Using the .jar file**

In another java gradle project, add the `lib.jar` file to the `libs` directory

Add the following line to the `build.gradle` configuration
```
implementation fileTree(dir: "libs", include: ["*.jar"])
```


# Documentation

**asr-norm-vn** is a tool used for Vietnamese Speech To Text text normalization. The tool will accept a spoken form string as input, and output its normalized written form version.

**Example**  
**Input:** hai trăm hai mươi nhân viên mới tại techcombank hà nội đã tham gia chương trình tập huấn  
**Output:** 220 nhân viên mới tại Techcombank Hà Nội đã tham gia chương trình tập huấn

**asr-norm-vn** is a part of *Vinfast Virtual Assistant*, *VSmart Voice Control* and *VKeyboard*


# Requirements

- Android API 21 or higher
- Java 8 or higher


# Documentation

## I. Overall

The tool is based mainly on 2 main concepts: **Entity** and **Normalizer**. Different type of entities (number, address, proper name, ...etc) are manipulated by Normalizer instances to transform a spoken form text to its written form version.

## II. Supported Entity

**LexicanRegexEntity** // vê tê vê => vtv

**SpecialFullNameEntity** // phil mickelson => Phil Mickelson

**PersonNameEntity** // cao hoàng tùng => Cao Hoàng Tùng

**CountryMapEntity** // việt nam => Việt Nam

**ProvinceMapEntity** // hà nội => Hà Nội

**DistrictMapEntity** // hai bà trưng => Hai Bà Trưng

**POIMapEntity** // chùa trấn quốc => Chùa Trấn Quốc

**SerialNumberRegexEntity** // không ba tám ba sáu một không sáu bảy => 038361067

**MonthRegexEntity** // tháng mười hai => tháng 12

**YearRegexEntity** // năm hai ngàn lẻ sáu => năm 2006

**MonthYearCountRegexEntity** // ba năm => 3 năm

**NumberRegexEntity**, // một triệu hai trăm ba mươi ngàn => 1230000

**PunctuationRegexEntity**8 // 1 phẩy 3 => 1.3 ; 1 trên 3 trên 8 => 1/3/8

**TimeEntityRegexEntity** // 1 giờ 10 => 1:10

**SqrtCalculationRegexEntity**,  // căn 2 => √2

**SimpleCalculationRegexEntity**  // 1 cộng 3 => 1 + 3

**UnitRegexEntity**  // 1 ki lô mét => 1 km

**AbbreviationMapEntity** // vtv => VTV

**SegmentRegexEntity**  // 3 % => 3% ; 3 d => 3d

**WrittenSerialNumberCharacterRegexEntity**  // 11db6 => 11DB6

**AppNameRegexEntity**  // báo hay 24 giờ => Báo Hay 24h

**WebsiteNameRegexEntity**  // google chấm com chấm vn => google.com.vn


## III. Supported Normalizer

Use the normalizer inside a Java or Android application

### a. VinFastVoiceControlNormalizer
```java
VinFastVoiceControlNormalizer myNormalizer = new VinFastVoiceControlNormalizer();
String text = "tăng âm lượng thêm năm mươi phần trăm";
String output = myNormalizer.normText(text); // "tăng âm lượng thêm 50%"
```

### b. VoiceControlNormalizer
```java
VoiceControlAsrNormalizer myNormalizer = new VoiceControlAsrNormalizer();
String text = "tăng âm lượng thêm năm mươi phần trăm";
String output = myNormalizer.normText(text); // "tăng âm lượng thêm 50%"
```

### c. VKeyboardNormalizer
```java
VkeyAsrNormalizer myNormalizer = new VkeyAsrNormalizer();
String text = "một triệu cộng hai trăm nghìn";
String output = myNormalizer.normText(text); // "1000000 + 200000"
```


## IV. Further developments
### a. Creating new normalize rule
To create a new normalize rule for the tool, follow these 2 steps:

#### Step 1: Create a new Entity
Every new entity class must extends from the existing abstract class **BaseEntity**.

```java
abstract public class BaseEntity {
    protected JSONObject globalConfig;

    public JSONObject getGlobalConfig() {
        return globalConfig;
    }

    /**
     * Get the type string for the entity
     * @return
     */
    abstract public String getType();

    /**
     * norm a single entity text
     * @param spokenFormEntityString
     * @return
     */
    abstract public String normEntity(String spokenFormEntityString);


    public BaseEntity(JSONObject config) {
        this.globalConfig = config;
    }

    /**
     * Get a list of entities
     * @param text
     * @return
     */
    abstract public EntityObject[] getEntities(String text);

    /**
     * Check if the entity string should be normed or not
     * @param spokenFormEntity
     * @param contextLeft
     * @param contextRight
     * @return
     */
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        return false;
    }
}
```

- **getType**(): String  
  Return a name for the entity. This string can later be used as key to load configuration from config files

- **normEntity**( **spokenFormEntityString**: String ): String  
  Take in a spoken form entity string and return its written form version. *Note:* **spokenFormEntityString** must be the **exact** entity string, not a string that contain the entity (Eg: "hai mươi ba", not "số hai mươi ba")

- **getEntities**( **text**: String ): EntityObject[]  
  Take in a full text and return a list of **EntityObject**. An EntityObject includes the start index and end index, the type, the entity text and its spoken form replacement. See **vin.bigdata/asrnormalizer/entityobject/EntityObject.java** for more

- **isException**( **spokenFormEntity**: String, **contextLeft**: String[], **contextRight**: String[] ): boolean  
  Take in the spoken form entity string, list of token on the left and list of token on the right. Return **true** if the string should be replaced by its spoken form version, **false** otherwise


```java
public class MyEntity extends BaseEntity {
// overwritting needed function
}
```

#### Step 2: Pass the new entity instance to a BaseNormalizer instance
```java
// get the config object for VKeyboard
NormalizerConfig vKeyNormalizerConfig = new VKeyNormalizerConfig(); 
JSONObject config = vKeyNormalizerConfig.getConfig();

// create the normalizer assosiated with the entity
BaseNormalizer myNormalizer = new BaseNormalizer(new MyEntity(config));
myNormalizer.normFullText("..."); // return the normalized version of the text
```

Different type of Entity can be put next to each other to create a pipeline for the normalization process

```java
BaseNormalizer[] normalizers;
normalizers = new BaseNormalizer[]{
                new BaseNormalizer(new LexicanRegexEntity(config)), // vê tê vê => vtv
                new BaseNormalizer(new SpecialFullNameEntity(config)), // phil mickelson => Phil Mickelson
                new BaseNormalizer(new PersonNameEntity(config)), // cao hoàng tùng => Cao Hoàng Tùng
                ...
                new BaseNormalizer(new MyEntity(config))
};

String text = "...";
for (BaseNormalizer normalizer : normalizers) {
    text = normalizer.normFullText(text);
}
```

### b. Extending from current abstract Entity classes
There are some defined classes which can be used instead of **BaseEntity**
- **MapBaseEntity** - Use to recognize and norm entity based on a predefined mapping (tokenFrom => tokenTo)
    + **NameMapEntity**

- **RegexBaseEntity** - Use to recognize and norm entity based on a predefined regex rule set
    + **SimpleRegexEntity**
    + **ReplaceRegexEntity**

- **BinarySetBaseEntity** - Use to recognize and norm entity based on a predefined set of tokens (tokenFrom => tokenTo). This set must be compressed into binary format
- **NumberFSTEntity** - Use to recognize and norm number base on a Finite State Tranducer configuration



### c. Using Configuration
The application works on a global config JSONObject, which each key being associated with a specific entity

The config files are stored in the folder /asr-norm/src/main/assets/resources/cfg


**Synonyms for config string in RegexBaseEntity classes**  
If a config key has been added to the global config JSONObject, another config may reuse that config.


There are 2 types of synonyms for RegexBaseEntity classes, which can be used in the configuration file

```##$dict_object$##```  
Example:
```
{
   "key1": {
      "dict": {
         "một": "1",
         "hai": "2",
         "ba": "3",
      }
   },
   "key2": {
      "pattern": "##$key1.dict$##"
   }
}
```  
will be equivalent to
```
{
   "key1": {
      "dict": {
         "một": "1",
         "hai": "2",
         "ba": "3",
      }
   },
   "key2": {
      "pattern": "(một|hai|ba)"
   }
}
```

```###string_object###```  
Example:
```
{
   "key1": {
      "key1.0": "###number_pattern###",
      "dict": {
         "một": "1",
         "hai": "2",
         "ba": "3",
      }
   },
   "number_pattern": {
      "prefix": "(prefix)",
      "postfix": "(postfix)",
      "pattern": "một|hai|ba",
      "group": 0,
   }
}
```  
will be equivalent to
```
{
   "key1": {
      "key1.0": "(prefix)một|hai|ba(postfix)",
      "dict": {
         "một": "1",
         "hai": "2",
         "ba": "3",
      }
   },
   "number_pattern": {
      "prefix": "(prefix)",
      "postfix": "(postfix)",
      "pattern": "một|hai|ba",
      "group": 0,
   }
}
```

**Create a new configuration**   
Create a new normalizer config object
```java
public class MyNormalizerConfig extends BaseNormalizerConfig {
    public MyNormalizerConfig(Context context) {
        super(context);
        
        JSONObject myConfig = # Load your config #

        // add myConfig to the global config object assiciated with key "myKey"
      this.config.put("key1", ConfigUtilities.getConfigFromFile(context, "resources/cfg/entitycfg/{folder}/myConfig1.json"));
      this.config.put("key2", ConfigUtilities.getConfigFromFile(context, "resources/cfg/entitycfg/{folder}/myConfig2.json"));
      this.config.put("key3", ConfigUtilities.getConfigFromFile(context, "resources/cfg/entitycfg/{folder}/myConfig3.json"));
    }
}
```

**Create the associated config file in ```src/main/assets/resources/cfg/entitycfg/{folder}/myConfig1.json```**
```
{
  "key1": {...}
}
```

The final config will be a JSONObject with items added from this.config.put(...) method

Each **Entity** object will read the config from this final config and do the logic you implement!