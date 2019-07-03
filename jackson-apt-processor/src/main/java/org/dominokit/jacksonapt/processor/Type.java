/*
 * Copyright 2017 Ahmad Bawaneh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dominokit.jacksonapt.processor;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.SimpleTypeVisitor8;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.typeUtils;
import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.elementUtils;

/**
 * <p>Type class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class Type {

    private static final int FIRST_ARGUMENT = 0;
    private static final int SECOND_ARGUMENT = 1;
    /** Constant <code>BEAN_JSON_SERIALIZER_IMPL="BeanJsonSerializerImpl"</code> */
    public static final String BEAN_JSON_SERIALIZER_IMPL = "BeanJsonSerializerImpl";
    /** Constant <code>BEAN_JSON_DESERIALIZER_IMPL="BeanJsonDeserializerImpl"</code> */
    public static final String BEAN_JSON_DESERIALIZER_IMPL = "BeanJsonDeserializerImpl";
    /**
     * <p>wrapperType.</p>
     *
     * @param type a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link com.squareup.javapoet.TypeName} object.
     */
    public static TypeName wrapperType(TypeMirror type) {
        if (isPrimitive(type)) {
            if ("boolean".equals(type.toString())) {
                return TypeName.get(Boolean.class);
            } else if ("byte".equals(type.toString())) {
                return TypeName.get(Byte.class);
            } else if ("short".equals(type.toString())) {
                return TypeName.get(Short.class);
            } else if ("int".equals(type.toString())) {
                return TypeName.get(Integer.class);
            } else if ("long".equals(type.toString())) {
                return TypeName.get(Long.class);
            } else if ("char".equals(type.toString())) {
                return TypeName.get(Character.class);
            } else if ("float".equals(type.toString())) {
                return TypeName.get(Float.class);
            } else if ("double".equals(type.toString())) {
                return TypeName.get(Double.class);
            } else {
                return TypeName.get(Void.class);
            }
        } else {
            return TypeName.get(type);
        }
    }

    private static boolean isPrimitive(TypeMirror typeMirror) {
        return typeMirror.getKind().isPrimitive();
    }

    /**
     * <p>isPrimitiveArray.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isPrimitiveArray(TypeMirror typeMirror) {
        return (isArray(typeMirror) && isPrimitive(arrayComponentType(typeMirror))) || isPrimitive2dArray(typeMirror);
    }

    private static boolean isPrimitive2dArray(TypeMirror typeMirror) {
        return is2dArray(typeMirror) && isPrimitiveArray(arrayComponentType(typeMirror));
    }

    /**
     * <p>isArray.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isArray(TypeMirror typeMirror) {
        return TypeKind.ARRAY.compareTo(typeMirror.getKind()) == 0;
    }

    /**
     * <p>is2dArray.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean is2dArray(TypeMirror typeMirror) {
        return isArray(typeMirror) && isArray(arrayComponentType(typeMirror));
    }

    /**
     * <p>arrayComponentType.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link javax.lang.model.type.TypeMirror} object.
     */
    public static TypeMirror arrayComponentType(TypeMirror typeMirror) {
        return ((ArrayType) typeMirror).getComponentType();
    }

    /**
     * <p>deepArrayComponentType.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link javax.lang.model.type.TypeMirror} object.
     */
    public static TypeMirror deepArrayComponentType(TypeMirror typeMirror) {
        TypeMirror type = ((ArrayType) typeMirror).getComponentType();
        return isArray(type) ? arrayComponentType(type) : type;
    }

    /**
     * <p>isEnum.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isEnum(TypeMirror typeMirror) {
        return !isNull(ObjectMapperProcessor.typeUtils.asElement(typeMirror))
                && !Type.isPrimitive(typeMirror)
                && !Type.isPrimitiveArray(typeMirror)
                && ElementKind.ENUM.compareTo(ObjectMapperProcessor.typeUtils.asElement(typeMirror).getKind()) == 0;
    }

    /**
     * <p>isCollection.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isCollection(TypeMirror typeMirror) {
        return !Type.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Collection.class);
    }

    /**
     * <p>isIterable.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isIterable(TypeMirror typeMirror) {
        return !Type.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Iterable.class);
    }

    /**
     * <p>isAssignableFrom.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @param targetClass a {@link java.lang.Class} object.
     * @return a boolean.
     */
    public static boolean isAssignableFrom(TypeMirror typeMirror, Class<?> targetClass) {
        return ObjectMapperProcessor.typeUtils.isAssignable(typeMirror, ObjectMapperProcessor.typeUtils.getDeclaredType(ObjectMapperProcessor.elementUtils.getTypeElement(targetClass.getName())));
    }

    /**
     * <p>isMap.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isMap(TypeMirror typeMirror) {
        return !Type.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Map.class);
    }

    /**
     * <p>firstTypeArgument.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link javax.lang.model.type.TypeMirror} object.
     */
    public static TypeMirror firstTypeArgument(TypeMirror typeMirror) {
        return ((DeclaredType) typeMirror).getTypeArguments().get(FIRST_ARGUMENT);
    }

    /**
     * <p>secondTypeArgument.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link javax.lang.model.type.TypeMirror} object.
     */
    public static TypeMirror secondTypeArgument(TypeMirror typeMirror) {
        return ((DeclaredType) typeMirror).getTypeArguments().get(SECOND_ARGUMENT);
    }

    /**
     * <p>isBasicType.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isBasicType(TypeMirror typeMirror) {
        return TypeRegistry.isBasicType(typeMirror.toString());
    }

    /**
     * <p>getPackage.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getPackage(TypeMirror typeMirror) {
        return ObjectMapperProcessor.elementUtils.getPackageOf(ObjectMapperProcessor.typeUtils.asElement(typeMirror)).getSimpleName().toString();
    }

    /**
     * <p>simpleName.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link javax.lang.model.element.Name} object.
     */
    public static Name simpleName(TypeMirror typeMirror) {
        return ObjectMapperProcessor.typeUtils.asElement(typeMirror).getSimpleName();
    }

    /**
     * <p>serializerName.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.String} object.
     */
    public static String serializerName(TypeMirror typeMirror) {
        return serializerName(
        	elementUtils.getPackageOf(typeUtils.asElement(typeUtils.erasure(typeMirror))).toString(), 
        	typeMirror);
    }

    /**
     * <p>serializerName.</p>
     *
     * @param packageName a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String serializerName(String packageName, TypeMirror beanType) {
        return packageName + "." + stringifyType(beanType) + BEAN_JSON_SERIALIZER_IMPL;
    }

    /**
     * <p>deserializerName.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.String} object.
     */
    public static String deserializerName(TypeMirror typeMirror) {
        return deserializerName(
       		elementUtils.getPackageOf(typeUtils.asElement(typeUtils.erasure(typeMirror))).toString(), 
        	typeMirror);
    }
    
    /**
     * <p>deserializerName.</p>
     *
     * @param packageName a {@link java.lang.String} object.
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object
     * @return a {@link java.lang.String} object.
     */
    public static String deserializerName(String packageName, TypeMirror beanType) {
        return packageName + "." + stringifyType(beanType) + BEAN_JSON_DESERIALIZER_IMPL;
    }
    
    /**
     * <p>stringifyType</p>
     * Stringify given TypeMirror including generic arguments
     *  
     * @param type a {@link javax.lang.model.type.TypeMirror} object
     * @return a {@link java.lang.String} containing string representation of given TypeMirror
     */
    public static String stringifyType(TypeMirror type) {
    	return type.accept(new SimpleTypeVisitor8<String, String>() {
			@Override
			public String visitDeclared(DeclaredType t, String p) {
				return 
					p 
					+ t.asElement().getSimpleName()
					+ ((!t.getTypeArguments().isEmpty())?
						"_" + t.getTypeArguments().stream().map(type -> visit(type, "")).collect(Collectors.joining("_"))
						: "");
			}

    		@Override
    		public String visitWildcard(WildcardType t, String p) {
    			return 
    				p
    				+ ((t.getExtendsBound() != null)? 
    						"extends" + visit(t.getExtendsBound(), "") 
    						:(t.getSuperBound() != null)?
    								"super" + visit(t.getSuperBound(), "")
    								: "");
    		}
    	}, "");
    }
    
    /**
     * <p>generateDeserializer.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.String} object.
     */
    public static String generateDeserializer(TypeMirror typeMirror) {
        return new 
        	DeserializerGenerator().generate(
        		typeMirror, 
        		elementUtils.getPackageOf(typeUtils.asElement(typeUtils.erasure(typeMirror))).toString());
    }

    /**
     * <p>generateSerializer.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.String} object.
     */
    public static String generateSerializer(TypeMirror typeMirror) {
        return new 
        	SerializerGenerator().generate(
        		typeMirror, 
        		elementUtils.getPackageOf(typeUtils.asElement(typeUtils.erasure(typeMirror))).toString());
    }
    
    /**
     * If given type is bounded wildcard, remove the wildcard and returns extends bound 
     * if exists. If extends bounds is non existing - return the super bound.
     * 
     * 
     * If given type is not wildcard, returns type.
     * 
     * @param type TypeMirror to be processed
     * @return extends or super bounds for given wildcard type
     */
    public static TypeMirror removeOuterWildCards(TypeMirror type) {
    	return type.accept(new SimpleTypeVisitor8<TypeMirror, Void>() {
    		@Override
			public TypeMirror visitDeclared(DeclaredType t, Void p) {
    			return t;
    		}
    		
    		@Override
    		public TypeMirror visitTypeVariable(TypeVariable t, Void p) {
    			return t;
    		}
    		
    		@Override
    		public TypeMirror visitWildcard(WildcardType t, Void p) {
    			return 
    					t.getExtendsBound() != null?
    						visit(t.getExtendsBound())
    						: t.getSuperBound() != null?
    							visit(t.getSuperBound())
    							: typeUtils.getDeclaredType(elementUtils.getTypeElement(Object.class.getName()));    				
    		}
    		
    		@Override
    		public TypeMirror visitArray(ArrayType t, Void p) {
    			return typeUtils.getArrayType(visit(t.getComponentType()));
    		}
    		
    		@Override
    		public TypeMirror visitPrimitive(PrimitiveType t, Void p) {
    			return t;
    		}
    		
    	}, null);
    		
    }
    
    /**
     * Check if given TypeMirror has wildcards
     * @param type
     * @return true if given TypeMirror has wildcards
     */
    public static boolean hasWildcards(TypeMirror type) {
    	return type.accept(new SimpleTypeVisitor8<Boolean, Void>() {
    		@Override
			public Boolean visitDeclared(DeclaredType t, Void p) {
    			return		
    				t.getTypeArguments().stream()
    					.map(typeArg -> visit(typeArg, null))
    					.filter(b -> b)
    					.findFirst().orElse(false);
    		}
    		
    		@Override
    		public Boolean visitTypeVariable(TypeVariable t, Void p) {
    			return false;
    		}
    		
    		@Override
    		public Boolean visitWildcard(WildcardType t, Void p) {
    			return true;				
    		}
    		
    		@Override
    		public Boolean visitArray(ArrayType t, Void p) {
    			return visit(t.getComponentType());
    		}
    		
    		@Override
    		public Boolean visitPrimitive(PrimitiveType t, Void p) {
    			return false;
    		}
    		
    	}, null);
    }
    
    /**
     * Check if given TypeMirror is a generic Java type
     * @param type
     * @return true if given TypeMirror is a generic type 
     */
    public static boolean isGenericType(TypeMirror type) {
    	return type.accept(new SimpleTypeVisitor8<Boolean, Void>() {
    		@Override
			public Boolean visitDeclared(DeclaredType t, Void p) {
    			return !t.getTypeArguments().isEmpty();
    		}
    		
    		@Override
    		public Boolean visitTypeVariable(TypeVariable t, Void p) {
    			return true;
    		}
    		
    		@Override
    		public Boolean visitWildcard(WildcardType t, Void p) {
    			return true;
    		}
    		
    		@Override
    		public Boolean visitArray(ArrayType t, Void p) {
    			return visit(t.getComponentType());
    		}
    		
    		@Override
    		public Boolean visitPrimitive(PrimitiveType t, Void p) {
    			return false;
    		}
    		
    	}, null);
    }
    
    /**
     * Check if given TypeMirror has type parameter(s).
     * @param type
     * @return true if given TypeMirror has type parameter(s)
     */
    public static boolean hasTypeParameter(TypeMirror type) {
    	return type.accept(new SimpleTypeVisitor8<Boolean, Void>() {
    		@Override
			public Boolean visitDeclared(DeclaredType t, Void p) {
    			return 
    				t.getTypeArguments().stream()
    					.map(typeArg -> visit(typeArg, null))
    					.filter(b -> b)
    					.findFirst()
    					.orElse(false);
    		}
    		
    		@Override
    		public Boolean visitTypeVariable(TypeVariable t, Void p) {
    			return true;
    		}
    		
    		@Override
    		public Boolean visitWildcard(WildcardType t, Void p) {
    			return 
    				t.getExtendsBound() != null?
    					visit(t.getExtendsBound())
    					: t.getSuperBound() != null?
    						visit(t.getSuperBound())
    						: false;    			
    		}
    		
    		@Override
    		public Boolean visitArray(ArrayType t, Void p) {
    			return visit(t.getComponentType());
    		}
    		
    		@Override
    		public Boolean visitPrimitive(PrimitiveType t, Void p) {
    			return false;
    		}
    		
    	}, null);
    }
    
    /**
	 * Returns all subtypes described with @JsonSubInfo and @JsonSubType for given TypeMirror
	 * @param type TypeMirror to be inspected for subtypes
	 * @return SubTypesInfo object containing information for subtypes
	 */
	public static SubTypesInfo getSubTypes(TypeMirror type) {
		SubTypesInfo subTypeInfo = SubTypesInfo.emtpy();
		if (type.getKind() == TypeKind.DECLARED) {
			TypeElement beanElement =  (TypeElement)((DeclaredType)type).asElement();
			JsonTypeInfo typeInfo = beanElement.getAnnotation(com.fasterxml.jackson.annotation.JsonTypeInfo.class);
			if (typeInfo != null && typeInfo.use() == JsonTypeInfo.Id.NAME ) {
				if (beanElement.getAnnotation(com.fasterxml.jackson.annotation.JsonSubTypes.class) != null) {
					subTypeInfo = new SubTypesInfo(
						typeInfo.include(), 
						typeInfo.property().isEmpty() ? 
							typeInfo.use().getDefaultPropertyName() 
							: typeInfo.property(), 
						getSubtypeTypeMirrors(beanElement));
				}
			}
		}
		return subTypeInfo;
	}
	
	/**
	 * Iterate over JsonSubTypes.Type annotations and converts them to a map 
	 * @param element
	 * @return  map of JsonSubTypes.Type.name (as String) and JsonSubTypes.Type.value (as TypeMirror)
	 */
	// Retrieving Class<?> from Annotation can be tricky in Annotation processor
	// See https://area-51.blog/2009/02/13/getting-class-values-from-annotations-in-an-annotationprocessor/
	@SuppressWarnings("unchecked")
	private static Map<String, TypeMirror> getSubtypeTypeMirrors(Element element) {
		List<? extends AnnotationMirror> subTypes = element.getAnnotationMirrors().stream()
				.filter(am -> am.getAnnotationType().asElement().getSimpleName().toString().equals("JsonSubTypes")) // Get JsonSubType annotation mirror
				.flatMap(am-> am.getElementValues().entrySet().stream()) // do a flat map for JsonSubType element values map entries
				.filter(entry -> entry.getKey().getSimpleName().toString().equals("value")) //find the "value" element of JsonSubType
				.flatMap(entry -> ((List<AnnotationMirror>)entry.getValue().getValue()).stream()) // treat JsonSubType.value() as list of annotation mirrors of JsonSubType.Type
				.collect(Collectors.toList());
		
		return 
			subTypes.stream()
				.collect(
					Collectors.toMap(
						am -> am.getElementValues().entrySet().stream() // create a stream from all element values map entries for a given JsonSubType.Type
							.filter(entry -> entry.getKey().getSimpleName().toString().equals("name")) // find "name" element 
							.map(entry-> (String)entry.getValue().getValue()) // get the value from "name" element, which is a String
							.findFirst()
							.orElse(null),
						am -> am.getElementValues().entrySet().stream() // create a stream from all element values map entries for a given JsonSubType.Type
							.filter(entry -> entry.getKey().getSimpleName().toString().equals("value")) // find "name" element
							.map(entry-> (TypeMirror)entry.getValue().getValue())
							.findFirst()
							.orElse(null)));
		
	}
	
	/**
	 * Create TypeMirror for given generic type, with type parameters replaced by actual type arguments,
	 * specified in parametersToArgumentsMap
	 * @param type TypeMirror to be processed
	 * @param parametersToArgumentsMap mapping type parameter elements to types
	 * @return TypeMirror having type parameters replaced by actual type arguments
	 */
	public static TypeMirror getDeclaredType(TypeMirror type, Map<? extends TypeParameterElement, ? extends TypeMirror> parametersToArgumentsMap) {
    	return type.accept(new SimpleTypeVisitor8<TypeMirror, Void>() {
    		@Override
    		public TypeMirror visitDeclared(DeclaredType t, Void p){
    			return typeUtils.getDeclaredType(
    					(TypeElement)t.asElement(), 
    					t.getTypeArguments().stream().map(arg -> arg.accept(this, null)).toArray(TypeMirror[]::new));

    		}
    		
    		@Override
    		public TypeMirror visitTypeVariable(TypeVariable t, Void p){
    			return parametersToArgumentsMap.get(typeUtils.asElement(t));
    		}

    		@Override
    		public TypeMirror visitPrimitive(PrimitiveType t, Void p) {
    			return t;
    		}

    		@Override
    		public TypeMirror visitArray(ArrayType t, Void p){
    			return typeUtils.getArrayType(t.getComponentType().accept(this, null));

    		}
    		
    		@Override
    		public TypeMirror visitWildcard(WildcardType t, Void p){
    			return typeUtils.getWildcardType(
    					(t.getExtendsBound() != null)?
    						t.getExtendsBound().accept(this, null)
    						: null, 
    					(t.getSuperBound() != null)?
    						t.getSuperBound().accept(this, null)
    						: null);
    			
    	    }

    	}, null);
    }
	
	/**
	 * Check if given type has type argument containing unbounded wildcard
	 * @param type TypeMirror to be checked
	 * @return true if given type has type argument containing unbounded wildcard
	 */
	public static boolean hasUnboundedWildcards(TypeMirror type) {
		return type.accept(new SimpleTypeVisitor8<Boolean, Void>(){
			@Override
			public Boolean visitDeclared(DeclaredType t, Void p) {
				return t.getTypeArguments().stream()
						.map(typeArg -> visit(typeArg, null))
						.filter(b -> b)
						.findFirst()
						.orElse(false);
			}
			@Override
			public Boolean visitWildcard(WildcardType t, Void p) {
				return
						t.getExtendsBound() != null?
							visit(t.getExtendsBound(), null)
							: t.getSuperBound() != null?
								visit(t.getSuperBound(), null)
								: true;
								
			}
			
			@Override
			public Boolean visitPrimitive(PrimitiveType t, Void p) {
				return false;
			}
			
			@Override
			public Boolean visitTypeVariable(TypeVariable t, Void p) {
				return false;
			}
			
			@Override
			public Boolean visitArray(ArrayType t, Void p) {
				return visit(t.getComponentType(), null);
			}
			
		},  null); 
	}
	
	/**
	 * Check if given type is generic class (and not being collection, iterable, enum or map)
	 * with type argument containing bounded wildcard.
	 * 
	 * All type parameters of the type needs to be resolved to actual type arguments prior calling this method.
	 * Note that presence of type parameter causes RuntimeException.
	 * @param type TypeMirror to be checked. 
	 * @return true of type is generic class with type argument containing bounded wildcards
	 */
	public static boolean hasTypeArgumentWithBoundedWildcards(TypeMirror type) {
		return type.accept(new SimpleTypeVisitor8<Boolean, Boolean>(){
			@Override
			public Boolean visitDeclared(DeclaredType t, Boolean p) {
				final Boolean isCustomType =  
						!Type.isCollection(t) 
						&& !Type.isIterable(t)
						&& !Type.isMap(t)
						&& !Type.isEnum(t);
				
				return t.getTypeArguments().stream()
						.map(typeArg -> visit(typeArg, p || isCustomType))
						.filter(b -> b)
						.findFirst()
						.orElse(false);
			}
			@Override
			public Boolean visitWildcard(WildcardType t, Boolean p) {
				return
						t.getExtendsBound() != null?
							p || visit(t.getExtendsBound(), p)
							: t.getSuperBound() != null?
								p || !visit(t.getSuperBound(), p)
								: false;
								
			}
			
			@Override
			public Boolean visitPrimitive(PrimitiveType t, Boolean p) {
				return false;
			}
			
			@Override
			public Boolean visitTypeVariable(TypeVariable t, Boolean p) {
				throw new RuntimeException("Unexpected type variable for:" + type);
			}
			
			@Override
			public Boolean visitArray(ArrayType t, Boolean p) {
				return visit(t.getComponentType(), p);
			}
			
		},  false); 
	}
}
