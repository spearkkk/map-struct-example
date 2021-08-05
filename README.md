# Map Struct

현재까지 entity -> dto, dto -> entity, dto -> dto 매핑작업을 생성자나 빌더를 이용해서 작업을 해왔다. 사실대로 말하면 계층(layer)에 따른 dto를 따로 두지 않고 entity를
그대로 사용한 경우가 많았다. 어디서부터 잘못되어온 것인지는 몰라도 모든 계층에서 entity를 직접 참조하지 않도록 하는 것이 목표였다.  
그렇게 수 많은 entity와 각 계층마다 사용하는 dto를 따로 두고 매핑작업을 진행하려고 했다. 문득 1~2년차 때, 누군가가 사용했던 Model Mapper가 생각났다. 그리고 이전에 무심코
읽었던 [블로그 글](https://baek.dev/post/15/)이 기억났다. 그리고 블로그 코멘트 중 Map Struct를 발견했다.

## Map Struct를 선택한 이유

여러 블로그에서 보면 인기도, 성능에 대한 글이 적지 않다. 몇 개만 읽어 보아도 충분히 Map Struct가 좋은 선택지라고 느낄수 있다. 간단한 설정으로 Spring Framework에서 사용하도록 bean으로
스캐닝할 수 있어 좋았다. 또한 롬복(lombok)처럼 어노테이션 기반으로 동작하고 컴파일 시점에 생성된 코드를 확인할 수 있다는 점이다.

## Map Struct를 사용하면서 겪었던 것들

### Spring Framework에서 사용하기

[공식 문서](https://mapstruct.org/documentation/stable/reference/html/#using-dependency-injection)에 따르면 `componentModel`를 '
spring’으로 지정하면 ‘@Autowired’를 넣어서 만들어 준다.

### Lombok의 Builder사용하기

Gradle 설정(build.gradle)
```
// To use Map Struct and Lombok together,
// Refer to this: https://github.com/mapstruct/mapstruct-examples/tree/master/mapstruct-lombok
implementation 'org.projectlombok:lombok'
implementation 'org.mapstruct:mapstruct:1.4.2.Final'

annotationProcessor 'org.mapstruct:mapstruct-processor:1.4.2.Final'
annotationProcessor 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok-mapstruct-binding:0.2.0'
```

## 사용 예시
### Entity 객체에서 DTO 객체로 매핑하기

All arguments constructor(ctor)를 접근 제한을 두어 외부에서 접근하지 못하게 막았다. 그리고 builder를 통해 생성할 수 있도록 접근 제한을 하지 않았다. Mapper의 구현체 역시도
builder를 이용하여 코드가 생성된다.  
구현체는 `MapperNameImpl`으로 생성된다.   
[MSE-001-basic-usage](https://github.com/spearkkk/map-struct-example/tree/feature/MSE-001-basic-usage)

Person entity를 PersonResponse dto로 매핑하는 방법이다. 추가적으로 스프링의 빈으로 등록하기 위해 @Mapper annotation에 `componentModel`의 값을 ‘spring’으로
주었다. 이 설정은 구현체에 @Component annotation이 생성한다.

### 상속 관계에서 매핑하기
```java
public class BaseDatetime {
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime lastModifiedAt;
}
```

Entity 마다 공통으로 들어가는 날짜에 대한 데이터는 따로 추출하고 entity 객체는 이를 상속하고 있다. 부모 객체의 있는 데이터도 mapper가 자동적으로 잘 매핑해준다.  
[MSE-002-inheritance-usage](https://github.com/spearkkk/map-struct-example/compare/feature/MSE-001-basic-usage...feature/MSE-002-inheritance-usage)

### 합성 관계에서 매핑하기

한 객체가 멤버 변수로 다른 객체를 가진 상황이다. 별다른 작업없이 mapper가 자동적으로 매핑해준다.   
[MSE-003-composition-usage](https://github.com/spearkkk/map-struct-example/compare/feature/MSE-002-inheritance-usage...feature/MSE-003-composition-usage)

그러나 객체의의 변수명이 달라진 경우는 @Mapping annotation을 이용해서 `source`와 `target`을 지정해주어야 한다.  
[MSE-003-composition-usage-diff-name](https://github.com/spearkkk/map-struct-example/compare/feature/MSE-003-composition-usage...feature/MSE-003-composition-usage-diff-name)

또한, 추가적인 매핑 로직이 필요한 경우 별도의 mapper를 두어서 로직을 구현할 수 있다. 그리고 이 별도로 구현한 mapper를 가지고 와서 사용한다.  
문제는 테스트 코드에서 별도의 mapper를 `Mappers.getMapper()`로 가지고 올 수 없다. 별도의 mapper를 사용할 때, 스프링 빈으로 주입해서 사용하고 있다는 점을 고려하면 테스트 코드도 이처럼
스프링 빈으로 가져와야 한다. 따라서 mapper의 구현체를 빈(bean)으로 가져와서 테스트 코드를 작성할 수 있다.  
[MSE-003-composition-usage-another-mapper](https://github.com/spearkkk/map-struct-example/compare/feature/MSE-003-composition-usage-diff-name...feature/MSE-003-composition-usage-another-mapper)  
[How to Get Mapper as Spring Bean](https://stackoverflow.com/questions/45275382/how-to-write-junit-test-for-mapstruct-abstract-mapper-injected-via-spring)

### 날짜 형식 지정해서 매핑하기

기본적으로 mapper는 자바 8의 날짜 클래스를 지원한다. (
참고: [link](https://mapstruct.org/documentation/stable/reference/html/#implicit-type-conversions))  
따로 형식을 지정하지 않으면 default 형식으로 동작한다. 만약 형식을 지정하고 싶다면 @Mapping annotation의 `dateFormat` 값을 지정하면 된다.  
[MSE-004-datetime-format-usage](https://github.com/spearkkk/map-struct-example/compare/feature/MSE-003-composition-usage-another-mapper...feature/MSE-004-datetime-format-usage)

### JSON을 사용하여 매핑하기

객체 데이터를 json value 또는 json value를 객체로 매핑할 수 있다. 기본적으로 제공하는 방법이 따로 있지는 않아 보여서 추상 클래스로 매핑하는 로직을 구현하였다.  
[MSE-005-json-usage](https://github.com/spearkkk/map-struct-example/compare/feature/MSE-004-datetime-format-usage...feature/MSE-005-json-usage)

### 두 개의 객체를 한 객체로 매핑하기

두 객체의 데이터로 한 객체로 매핑하기 위해서는 별도의 로직 없이 바로 매핑할 수 있다. 그러나 한 가지 유의할 점은 두 객체 안의 동일한 이름의 변수는 `source`와 `target`을 지정해주어야만 매핑할 수
있다.   
[MSE-006-objects-to-a-object](https://github.com/spearkkk/map-struct-example/compare/feature/MSE-005-json-usage...feature/MSE-006-objects-to-a-object)

### 두 개의 변수를 하나의 변수로 매핑하기

두 객체의 각각의 변수는 한 객체로 매핑할 때, 별도의 로직 구현이 필요없다. 그러나 두 개의 변수를 하나의 변수로 매핑할 때에는 별도의 로직 구현이 필요하다.  
[MSE-007-fields-to-a-field](https://github.com/spearkkk/map-struct-example/compare/feature/MSE-006-objects-to-a-object...feature/MSE-007-fields-to-a-field)

### 공통으로 사용하는 변수에 대한 매퍼를 추출하여 매핑하기

앞서 말했듯이 entity의 날짜 관련 데이터는 따로 관리한다. 다수의 entity 객체에서 동일한 형식으로 날짜를 매핑해주기 위해 하나로 통일하도록 한다. 새로운 annotation(
@BaseDatetimeToString)을 만들어 매핑 로직에 선언해준다면 해당 annotation의 설정을 그대로 사용하여 구현체를 만들어 준다.  
그러나 한 가지 유의할 점은 다수의 객체를 한 객체로 매핑할 때, `createdAt`처럼 동일한 이름의 변수는 자동으로 매핑할 수 없기 때문에 `source`와 `target`을 지정해주어야 한다.  
[MSE-008-common-fields-usage](https://github.com/spearkkk/map-struct-example/compare/feature/MSE-007-fields-to-a-field...feature/MSE-008-common-fields-usage)
