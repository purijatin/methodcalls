
import com.jp.agent.IgnoreMonitor;
import com.jp.agent.Monitor;
import com.jp.agent.MonitorInterceptor;
import com.jp.agent.Prac;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;

import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.*;


public class AgentSetup {
    public static void main(String[] args) throws CloneNotSupportedException, IOException, InterruptedException {
        setup();
        new Prac().go1();
        new Prac().go1();
    }



    private static void setup(){
        new AgentBuilder.Default()
                .type(declaresMethod(isAnnotatedWith(Monitor.class))
                        .and(not((isAnnotatedWith(IgnoreMonitor.class)))))
                .transform((builder, typeDescription, classLoader) ->
                    builder
                            .method(any())
                            .intercept(MethodDelegation.to(MonitorInterceptor.class))
                )
                .with(new AgentBuilder.Listener() {
                    @Override
                    public void onTransformation(TypeDescription typeDescription, DynamicType dynamicType) {
                        //System.out.println("1 "+typeDescription+" "+dynamicType);
                    }

                    @Override
                    public void onIgnored(TypeDescription typeDescription) {
//                        System.out.println("2 "+typeDescription.getCanonicalName()+" ");
                    }

                    @Override
                    public void onError(String typeName, Throwable throwable) {
                        System.out.println("3 "+typeName+" "+throwable);
                    }

                    @Override
                    public void onComplete(String typeName) {
                       // System.out.println("4 "+typeName);
                    }
                })
                .installOnByteBuddyAgent();
    }
}

