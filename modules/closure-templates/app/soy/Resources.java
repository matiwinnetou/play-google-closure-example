package soy;

import com.google.common.reflect.ClassPath;
import play.Application;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Resources {

    public static Set<ClassPath.ResourceInfo> resources(final Application application) {
        try {
            return ClassPath.from(application.classloader()).getResources();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<URL> findSoyFiles(final Set<ClassPath.ResourceInfo> resourceInfos) {
        try {
            return Resources.findInResources(resourceInfos, resource -> resource.getResourceName().endsWith(".soy"));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<URL> findXliffs(final Set<ClassPath.ResourceInfo> resourceInfos) {
        try {
            return Resources.findInResources(resourceInfos, resource -> resource.getResourceName().endsWith(".xlf"));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<URL> findInResources(final Set<ClassPath.ResourceInfo> resourceInfos,
                                              final Predicate<ClassPath.ResourceInfo> predicate) throws IOException {
        return resourceInfos.stream()
                .filter(predicate)
                .map(resource -> resource.url());
    }

}
