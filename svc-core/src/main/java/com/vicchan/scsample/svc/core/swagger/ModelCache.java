package com.vicchan.scsample.svc.core.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Function;
import com.vicchan.scsample.svc.core.swagger.model.ApiJsonObject;
import com.vicchan.scsample.svc.core.swagger.model.ApiJsonProperty;
import com.vicchan.scsample.svc.core.swagger.model.ApiJsonResult;
import com.vicchan.scsample.svc.core.swagger.model.ApiSingleParam;
import org.springframework.plugin.core.OrderAwarePluginRegistry;
import org.springframework.plugin.core.PluginRegistry;
import springfox.documentation.schema.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.TypeNameProviderPlugin;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.contexts.DocumentationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static com.vicchan.scsample.svc.core.common.GlobalString.*;
import static org.springframework.util.ObjectUtils.isEmpty;
import static springfox.documentation.schema.Collections.collectionElementType;
import static springfox.documentation.spi.schema.contexts.ModelContext.inputParam;

/**
 * Created by yueh on 2018/9/13.
 */
public class ModelCache {

  private Map<String, Model> knownModels = new HashMap<>();
  private DocumentationContext context;
  private Function<ResolvedType, ? extends ModelReference> factory;
  private TypeResolver typeResolver = new TypeResolver();
  private Map<String, ApiSingleParam> paramMap = new HashMap<>();
  private Class<?> cls;


  private ModelCache() {
  }


  public static ModelCache getInstance() {
    return ModelCacheSub.instance;
  }

  public ModelCache setParamMap(Map<String, ApiSingleParam> paramMap) {
    this.paramMap = paramMap;
    return getInstance();
  }

  public ModelCache setParamClass(Class<?> cls) {
    this.cls = cls;
    return getInstance();
  }

  public Class<?> getParamClass() {
    return cls;
  }


  public ModelCache setFactory(Function<ResolvedType, ? extends ModelReference> factory) {
    this.factory = factory;
    return getInstance();
  }

  public void setContext(DocumentationContext context) {
    this.context = context;
  }

  public DocumentationContext getContext() {
    return context;
  }

  public Map<String, Model> getKnownModels() {
    return knownModels;
  }


  public ModelCache addModel(ApiJsonObject jsonObj) {
    String modelName = jsonObj.name();

    knownModels.put( modelName,
        new Model( modelName,
            modelName,
            new TypeResolver().resolve( String.class ),
            "com.vicchan.scsample.svc.core.swagger.CommonData",
            toPropertyMap( jsonObj.value() ),
            "POST参数",
            "",
            "",
            newArrayList(), null, null
        ) );
    String resultName = jsonObj.name() + "-" + "result";

    knownModels.put( resultName,
        new Model( resultName,
            resultName,
            new TypeResolver().resolve( String.class ),
            "com.vicchan.scsample.svc.core.swagger.CommonData",
            toResultMap( jsonObj.result(), resultName ),
            "返回模型",
            "",
            "",
            newArrayList(), null, null
        ) );
    return ModelCacheSub.instance;
  }

  public Map<String, ModelProperty> toResultMap(ApiJsonResult jsonResult, String groupName) {
//        System.out.println("--- toResultMap ---");
    List<String> values = Arrays.asList( jsonResult.value() );
    List<String> outer = new ArrayList<>();
    //子model的名称
    String subModelName = null;
    if (jsonResult.name() != null && !jsonResult.name().equals( "" )) {
      subModelName = groupName + "-" + jsonResult.name();
    } else {
      subModelName = groupName;
    }
    if (!RESULT_TYPE_OTHER.equals( jsonResult.type() )) {
      outer.add( JSON_ERROR_CODE );
      outer.add( JSON_ERROR_MSG );
      if (!RESULT_TYPE_NORMAL.equals( jsonResult.type() )) {
        //model
        knownModels.put( subModelName,
            new Model( subModelName,
                subModelName,
                new TypeResolver().resolve( String.class ),
                "com.vicchan.scsample.svc.core.swagger.CommonData",
                transResultMap( values, subModelName ),
                "返回模型",
                "",
                "",
                newArrayList(), null, null
            ) );

        //prop
        Map<String, ModelProperty> propertyMap = new HashMap<>();

//                outer.add(jsonResult.name());
        Type t = null;
        if (RESULT_TYPE_OBJECT.equals( jsonResult.type() )) {
          //对象
          t = Object.class;
        } else {
          //列表
          t = List.class;
        }
        ResolvedType type = new TypeResolver().resolve( t );
        ModelProperty mp = new ModelProperty(
            jsonResult.name(),
            type,
            "",
            0,
            false,
            false,
            true,
            false,
            "",
            null,
            "",
            null,
            "",
            null,
            newArrayList()
        );
        // new AllowableRangeValues("1", "2000"),//.allowableValues(new AllowableListValues(["ABC", "ONE", "TWO"], "string"))
        //把ModelProperty的ResolvedType值关联成ModelReference的类型
        //但最后都会被下面的代码覆盖，因此此处似乎没有什么作用
        mp.updateModelRef( getModelRef() );
        ResolvedType collectionElementType = collectionElementType( type );
        try {
          Field f = ModelProperty.class.getDeclaredField( "modelRef" );
          f.setAccessible( true );
          if (RESULT_TYPE_OBJECT.equals( jsonResult.type() )) {
            //对象
            //new ModelRef(subModelName)表示关联已生成的Model，subModelName为Model名称
            f.set( mp, new ModelRef( subModelName ) );
          } else {
            //列表
            //new ModelRef("List", new ModelRef(subModelName))，第二个参数最终会变成itemModel
            //第一个参数可能是新ModelRef的名称（变量类型却是type），经测试填任何内容都会生成List，List中的内容为itemModel
            f.set( mp, new ModelRef( "List", new ModelRef( subModelName ) ) );
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        propertyMap.put( jsonResult.name(), mp );
        if (RESULT_TYPE_PAGE.equals( jsonResult.type() )) {
          outer.add( JSON_START_PAGE_NUM );
          outer.add( JSON_PAGE_SIZE );
          outer.add( JSON_TOTAL_COUNT );
        }
        propertyMap.putAll( transResultMap( outer ) );
        return propertyMap;
      }

      outer.addAll( values );
      return transResultMap( outer, subModelName );
    }

    return transResultMap( values, subModelName );
  }

  public Map<String, ModelProperty> transResultMap(List<String> values) {
    return transResultMap( values, null );
  }

  public Map<String, ModelProperty> transResultMap(List<String> values, String groupName) {
    Map<String, ModelProperty> propertyMap = new HashMap<>();
    for (String resultName : values) {
      ApiSingleParam param = paramMap.get( resultName );
      if (isEmpty( param )) {
        continue;
      }
      Class<?> type = param.type();
      if (!isEmpty( param )) {
        type = param.type();
      } else if (isEmpty( type )) {
        type = String.class;
      }

      boolean allowMultiple = param.allowMultiple();
      if (!isEmpty( param )) {
        allowMultiple = param.allowMultiple();
      }
      ResolvedType resolvedType = null;
      if (allowMultiple) {
        resolvedType = new TypeResolver().resolve( List.class, type );
      } else {
        resolvedType = new TypeResolver().resolve( type );
      }
      ModelProperty mp = new ModelProperty(
          resultName,
          resolvedType,
          param.type().getName(),
          0,
          false,
          false,
          true,
          false,
          param.value(),
          null,
          param.example(),
          null,
          "",
          null,
          newArrayList()
      );// new AllowableRangeValues("1", "2000"),//.allowableValues(new AllowableListValues(["ABC", "ONE", "TWO"], "string"))
      mp.updateModelRef( getModelRef() );

      //包含的子属性
      String[] items = param.items();
      if (type == Object.class && items != null && items.length > 0) {
        //当做对象处理，嵌套生成Model
        Map<String, ModelProperty> itemMap = transResultMap( Arrays.asList( items ), groupName );
        String subModelName = null;
        if (groupName != null && !groupName.equals( "" )) {
          //在对象名前加上组名前缀
          subModelName = groupName + "-" + resultName;
        }else{
          subModelName = resultName;
        }
        knownModels.put( subModelName,
            new Model( subModelName,
                subModelName,
                new TypeResolver().resolve( String.class ),
                "com.vicchan.scsample.svc.core.swagger.CommonData",
                itemMap,
                param.value(),
                "",
                "",
                newArrayList(), null, null
            ) );
        try {
          Field f = ModelProperty.class.getDeclaredField( "modelRef" );
          f.setAccessible( true );
          if (!allowMultiple) {
            //对象
            //new ModelRef(subModelName)表示关联已生成的Model，subModelName为Model名称
            f.set( mp, new ModelRef( subModelName ) );
          } else {
            //列表
            //new ModelRef("List", new ModelRef(subModelName))，第二个参数最终会变成itemModel
            //第一个参数可能是新ModelRef的名称（变量类型却是type），经测试填任何内容都会生成List，List中的内容为itemModel
            f.set( mp, new ModelRef( "List", new ModelRef( subModelName ) ) );
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      propertyMap.put( resultName, mp );
    }
    return propertyMap;
  }

  public Map<String, ModelProperty> toPropertyMap(ApiJsonProperty[] jsonProp) {
//        System.out.println("--- toPropertyMap ---");
    Map<String, ModelProperty> propertyMap = new HashMap<String, ModelProperty>();

    for (ApiJsonProperty property : jsonProp) {
      String propertyName = property.name();
      ApiSingleParam param = paramMap.get( propertyName );

      String description = property.description();
      if (isNullOrEmpty( description ) && !isEmpty( param )) {
        description = param.value();
      }
      //修复example问题
      String example = property.example();
      if (isNullOrEmpty( example ) && !isEmpty( param )) {
        example = param.example();
      }
      Class<?> type = property.type();
      if (!isEmpty( param )) {
        type = param.type();
      } else if (isEmpty( type )) {
        type = String.class;
      }

      boolean allowMultiple = property.allowMultiple();
      if (!isEmpty( param )) {
        allowMultiple = param.allowMultiple();
      }
      ResolvedType resolvedType = null;
      if (allowMultiple) {
        resolvedType = new TypeResolver().resolve( List.class, type );
      } else {
        resolvedType = new TypeResolver().resolve( type );
      }
//            System.out.println("----- example: " + example);
//            System.out.println("----- description: " + description);
      ModelProperty mp = new ModelProperty(
          propertyName,
          resolvedType,
          type.toString(),
          0,
          property.required(),
          false,
          property.readOnly(),
          null,
          description,
          null,
          example,
          null,
          property.defaultValue(),
          null,
          newArrayList()
      );// new AllowableRangeValues("1", "2000"),//.allowableValues(new AllowableListValues(["ABC", "ONE", "TWO"], "string"))
      mp.updateModelRef( getModelRef() );
      propertyMap.put( property.name(), mp );
    }

    return propertyMap;
  }


  private static class ModelCacheSub {
    private static ModelCache instance = new ModelCache();
  }

  private Function<ResolvedType, ? extends ModelReference> getModelRef() {
    Function<ResolvedType, ? extends ModelReference> factory = getFactory();
//        ModelReference stringModel = factory.apply(typeResolver.resolve(List.class, String.class));
    return factory;

  }


  public Function<ResolvedType, ? extends ModelReference> getFactory() {
    if (factory == null) {

      List<DefaultTypeNameProvider> providers = newArrayList();
      providers.add( new DefaultTypeNameProvider() );
      PluginRegistry<TypeNameProviderPlugin, DocumentationType> modelNameRegistry =
          OrderAwarePluginRegistry.create( providers );
      TypeNameExtractor typeNameExtractor = new TypeNameExtractor(
          typeResolver,
          modelNameRegistry,
          new JacksonEnumTypeDeterminer() );
      ModelContext modelContext = inputParam(
          context.getGroupName(),
          String.class,
          context.getDocumentationType(),
          context.getAlternateTypeProvider(),
          context.getGenericsNamingStrategy(),
          context.getIgnorableParameterTypes() );
      factory = ResolvedTypes.modelRefFactory( modelContext, typeNameExtractor );
    }
    return factory;
  }
}
