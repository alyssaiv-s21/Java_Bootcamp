package edu.school21.processer;

import com.google.auto.service.AutoService;
import edu.school21.annotation.HtmlForm;
import edu.school21.annotation.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@SupportedAnnotationTypes({"edu.school21.annotation.HtmlForm", "edu.school21.annotation.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
            for (Element element : annotatedElements) {
                if (element.getKind() == ElementKind.CLASS) {
                    generateHtmlForm((TypeElement) element);
                }
            }
        return true;
    }

    private void generateHtmlForm(TypeElement element) {
        HtmlForm htmlFormAnnotation = element.getAnnotation(HtmlForm.class);
        String fileName = htmlFormAnnotation.fileName();
        String action = htmlFormAnnotation.action();
        String method = htmlFormAnnotation.method();

        StringBuilder formContent = new StringBuilder();
        formContent.append("<form action=\"").append(action).append("\" method=\"").append(method).append("\">\n");
        for (Element enclosedElement : element.getEnclosedElements()) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                HtmlInput htmlInputAnnotation = enclosedElement.getAnnotation(HtmlInput.class);
                String type = htmlInputAnnotation.type();
                String name = htmlInputAnnotation.name();
                String placeholder = htmlInputAnnotation.placeholder();
                formContent.append("\t<input type=\"").append(type).append("\" name=\"").append(name);
                formContent.append("\" placeholder=\"").append(placeholder).append("\">\n");
            }
        }
        formContent.append("\t<input type=\"submit\" value=\"Send\">\n");
        formContent.append("</form>");
        saveHtmlForm(formContent.toString(), fileName);
    }
    private void saveHtmlForm(String content, String fileName) {
        try {
            Path outputPath = Paths.get("target/classes", fileName);
            BufferedWriter writer = Files.newBufferedWriter(outputPath);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.out.println("Can't save the file " + fileName);
        }
    }
}