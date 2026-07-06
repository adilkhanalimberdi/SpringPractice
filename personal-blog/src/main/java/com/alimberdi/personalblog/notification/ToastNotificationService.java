package com.alimberdi.personalblog.notification;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class ToastNotificationService implements NotificationService {

	public void sendSuccess(RedirectAttributes redirectAttributes, String message) {
		redirectAttributes.addFlashAttribute("toastType", "success");
		redirectAttributes.addFlashAttribute("toastMessage", message);
	}

	public void sendError(RedirectAttributes redirectAttributes, String message) {
		redirectAttributes.addFlashAttribute("toastType", "error");
		redirectAttributes.addFlashAttribute("toastMessage", message);
	}

	public void sendWarning(RedirectAttributes redirectAttributes, String message) {
		redirectAttributes.addFlashAttribute("toastType", "warning");
		redirectAttributes.addFlashAttribute("toastMessage", message);
	}

}
