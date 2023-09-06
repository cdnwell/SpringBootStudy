package tobyspring.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

// 사용하게 하려면 ImportSelector만 EnableMyAutoConfiguation에서 임포트 하면됨.
public class MyAutoConfigImportSelector implements DeferredImportSelector {
    private final ClassLoader classLoader;

    public MyAutoConfigImportSelector(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
//        return new String[] {
//                "tobyspring.config.autoconfig.DispatcherServletConfig",
//                "tobyspring.config.autoconfig.TomcatWebServerConfig"
//        };
        // 파일에서 자동 구성 대상 후보들을 읽어오는, 왜 후보냐 하면 파일에 어떤 @Configuration
        // 애노테이션을 넣어놓았다고 하면, 구성 정보로 쓰는 게 아니고 후보로 잡는 것
        // 그리고 이 중에서 어떤 것을 사용할 지는 스마트하게 결정 할 것.
        // 그래서 후보라고 불름
        // annotation의 클래스 정보를 넣는 특이한 메소드
        // ApplicationContext가 필요 하다면, ApplicationContextAware라는 인터페이스를 구현하면
        // 그 인터페이스의 setter 메서드를 이용해서, 컨테이너가 빈을 초기화 하면서 이 안에 빈을 주입
        // classLoader도 bean class loader라 할 수 있는데,(정확히는)
        // load는 ImportsCandidates라는 동일한 타입의 객체를 반환함.
        // 이 candidates에는 자동 구성에 사용할 configuration 클래스의 목록들이 들어 있을 것임.
        // ImportsCandidates는 자세히 보면, String 타입의 Iterable임
        // 이걸 Iterable<String>으로 받아도 됨.

        // 1.
//        ImportCandidates candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);

        // 2.
//        Iterable<String> candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);
//        return StreamSupport.stream(candidates.spliterator(), false).toArray(String[]::new);

        // 3. 이 Iterable은 순회를 다 하기전 알 수 없음
        // 그래서 List로 받음
        List<String> autoConfigs = new ArrayList<>();

//        for(String candidate : ImportCandidates.load(MyAutoConfiguration.class, classLoader)) {
//            autoConfigs.add(candidate);
//        }

        // 위 for문을 for each로 교체
//        ImportCandidates.load(MyAutoConfiguration.class, classLoader).forEach(candidate -> autoConfigs.add(candidate));

        // 메소드 레퍼런스로 대체,
        // 이렇게 선언하면 어떤 파일에서 이 정보를 읽어오지?
        // 어떤 파일에서 configuration class의 정보를 읽어오는가 하면.
        // class path에 META-INF/spring/full-qualified-name(앞의 패키지 이름까지 포함한 이름).imports 라는 파일에서
        // 읽어 온다.
        // 자바 소스 코드 외의 파일들은 resources라는 폴더 밑에 만듦.
        // 이 애놑이션 이름과 매칭되는 import파일에서 목록을 다 읽어와서 String array로 만든다음 리턴
        ImportCandidates.load(MyAutoConfiguration.class, classLoader).forEach(autoConfigs::add);



        // String 사이즈가 더 작으면 그 값을 무시하고 새로운 값을 넣어서 리턴해준다.
        // 사이즈를 모르니깐 빈 array를 가져다 넣어주고
        // toArray가 정확하게 String 타입을 리턴하게 함.
        return autoConfigs.toArray(new String[0]);

        // 자바 8.
        // type safe하게 array를 바꾸는 작업을 함.
//        return autoConfigs.stream().toArray(String[]::new);

        // Arrays.copyOf
        // 이렇게 하면 type safe하게 String collection을 String array로 만들 수 있음
//        return Arrays.copyOf(autoConfigs.toArray(), autoConfigs.size(), String[].class);
    }

    // implements BeanClassLoaderAware로 이 인터페이스를 구현하면 Spring Container가 여기다 빈을
    // 로딩할 때 사용하는 클래스 로더를 주입해줌.
    // 이렇게 변수에 집어넣어 놨다가 사용해도 되고
    // 2. 또 하나 방법은 생성자로 주입하는 것.

}
