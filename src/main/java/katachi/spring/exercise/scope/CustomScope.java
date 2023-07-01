package katachi.spring.exercise.scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.SessionScope;

import lombok.Data;

@Data
@Component
public class CustomScope extends SessionScope{
	/**
	 * Scopeインターフェースの実装クラスを作成
	 */
	private Map<String, Object> scopedObjects = Collections.synchronizedMap(new HashMap<String, Object>());

	private Map<String, Runnable> destructionCallbacks = Collections.synchronizedMap(new HashMap<String, Runnable>());


    /**
     * 基になるスコープから指定された名前のオブジェクトを返します。
     * Scope の中心的な操作であり、絶対に必要な唯一の操作です。
     */
	 @Override
	 public Object get(String name, ObjectFactory<?> objectFactory) {
	        Object scopeObject = scopedObjects.get(name);
	        if(scopeObject == null){
	            scopeObject = objectFactory.getObject();
	            scopedObjects.put(name, scopeObject);
	        }
	        return null;
	    }


	 /**
	     * 指定された name を持つオブジェクトを基になるスコープから削除します。
	     */
	 @Override
	 public Object remove(String name) {
		 Object scopeObject = scopedObjects.get(name);
		 if(scopeObject != null) {
			 scopedObjects.remove(name);
			 return scopeObject;
		 }else {
			 return null;
		 }
	 }




	    /**
	     * スコープ内の指定されたオブジェクトの破棄（またはスコープが個々のオブジェクトを破棄せず、その全体で終了する場合はスコープ全体の破棄）で実行されるコールバックを登録します。
	     */
	    @Override
	    public void registerDestructionCallback(String name, Runnable callback) {
	        destructionCallbacks.put(name, callback);
	    }
	    /**
	     * 指定されたキーがある場合、そのコンテキストオブジェクトを解決します。
	     */
	    @Override
	    public Object resolveContextualObject(String key) {
	        return null;
	    }
	    /**
	     * 存在する場合、現在の基礎となるスコープの会話 ID を返します。
	     */
	    @Override
	    public String getConversationId() {
	        return "custom";
	    }

}
