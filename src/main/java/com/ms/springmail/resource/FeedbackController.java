package com.ms.springmail.resource;

import com.ms.springmail.model.Feedback;
import com.ms.springmail.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/form")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public String sendForm(@RequestBody Feedback request) {
        return feedbackService.sendForm(request);
    }

}
