package br.com.rhm.dlimiter.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import br.com.rhm.dlimiter.DelimiterException;

public class ClassHandler {

	private Class<?> clazz;
	private Class<?> topLevelClass;
	private MemberHandler memberHandler;
	private Injector injector = new InjectorImpl();
	private DependencyMapperImpl dependencyMapper = new DependencyMapperImpl(this);
	private boolean valid;
	
	private ClassMemberFilter filter;
	
	public ClassHandler(Class<?> clazz){
		this(clazz, Object.class, new DelimiterFilter());
	}
	
	public ClassHandler(Class<?> clazz, Class<?> topLevelClass){
		this(clazz, topLevelClass, new DelimiterFilter());
	}
	
	public ClassHandler(Class<?> clazz, Class<?> topLevelClass, ClassMemberFilter filter){
		this(clazz, topLevelClass, filter, null);
	}
	
	public ClassHandler(Class<?> clazz, Class<?> topLevelClass, ClassMemberFilter filter, MemberHandler memberInst){
		super();
		this.clazz = clazz;
		this.topLevelClass = topLevelClass;
		this.filter = filter;
		this.memberHandler = 
				(memberInst != null ? 
						memberInst :
						new MemberHandler(filter == null ? new DelimiterFilter() : filter)
				);
	}
	
	public Collection<Field> getScannedFields() {
		return Collections.unmodifiableCollection(memberHandler.getScannedFields());
	}
	
	public Collection<Method> getScannedMethods() {
		return Collections.unmodifiableCollection(memberHandler.getScannedMethods());
	}
	
	public Map<String, Field> getMappedFields() {
		return Collections.unmodifiableMap(memberHandler.getMappedFields());
	}
	
	public Map<String, Method> getMappedMethods() {
		return Collections.unmodifiableMap(memberHandler.getMappedMethods());
	}
	
	public ClassHandler scan(){
		if (filter.accept(clazz)){
			mapClass(clazz);
			dependencyMapper.mapModelDependencies();
			valid = true;
		}
		return this;
	}
	
	public boolean isValid(){
		return valid;
	}
	
	private void mapClass(Class<?> clazz){
		if (!clazz.equals(topLevelClass)){
			mapClass(clazz.getSuperclass());

			Field[] fields = memberHandler.mapFields(clazz);
			memberHandler.mapGettersAndSetters(fields);
		}
	}
	
	public ClassMemberFilter getFilter(){
		return filter;
	}
	
	public Injector getInjector() {
		return injector;
	}
	
	public DependencyMapper getDependencyMapper() {
		return dependencyMapper;
	}
	
	public MemberHandler getMemberHandler() {
		return memberHandler;
	}
	
	public Class<?> getHandledClass(){
		return clazz;
	}
	
	public <T> T newInstance(){
		try{
			return (T) clazz.newInstance();
		}catch (Exception e) {
			throw new DelimiterException("There is no default constructor for the class [" + clazz.getName() + "]");
		}
	}
}