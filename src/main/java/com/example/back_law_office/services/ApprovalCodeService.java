package com.example.back_law_office.services;

import com.example.back_law_office.models.ApprovalCode;
import com.example.back_law_office.repositories.ApprovalCodeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalCodeService {

    @Autowired
    private ApprovalCodeRepository approvalCodeRepository;

    /**
     * Crea un nuevo código de aprobación.
     * @return El código de aprobación creado.
     */
    public ApprovalCode createApprovalCode() {
        ApprovalCode approvalCode = new ApprovalCode();
        approvalCode.setUsed(false);
        approvalCode.setCode(generateUniqueCode());
        return approvalCodeRepository.save(approvalCode);
    }

    /**
     * Obtiene todos los códigos de aprobación.
     * @return Una lista de códigos de aprobación.
     */
    public List<ApprovalCode> getAllApprovalCodes() {
        return approvalCodeRepository.findAll();
    }

    /**
     * Valida un código de aprobación.
     * @param code El código de aprobación a validar.
     * @return Un objeto Optional que contiene el código de aprobación si es válido, o vacío si no lo es.
     */
    public Optional<ApprovalCode> validateApprovalCode(String code) {
        return approvalCodeRepository.findByCodeAndUsed(code, false);
    }


   

    /**
     * Marca un código de aprobación como usado.
     * @param code El código de aprobación a marcar como usado.
     * @return El código de aprobación actualizado.
     */
    public ApprovalCode updateApprovalCode(Long id, ApprovalCode updatedApprovalCode) {
        return approvalCodeRepository.findById(id).map(existingApprovalCode -> {
            existingApprovalCode.setCode(updatedApprovalCode.getCode());
            existingApprovalCode.setUsed(updatedApprovalCode.getUsed());
            existingApprovalCode.setLegalAction(updatedApprovalCode.getLegalAction());
            return approvalCodeRepository.save(existingApprovalCode);
        }).orElseThrow(() -> new RuntimeException("Código de aprobación no encontrado con ID: " + id));
    }

    /**
     * Elimina un código de aprobación por su ID.
     * @param id
     */
    public void deleteApprovalCode(Long id) {
        if (approvalCodeRepository.existsById(id)) {
            approvalCodeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Código de aprobación no encontrado con ID: " + id);
        }
    }

    /**
     * Genera un código único de 6 dígitos.
     * @return Un código único de 6 dígitos.
     */
    private String generateUniqueCode() {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomDigit = (int) (Math.random() * 10);
            codeBuilder.append(randomDigit);
        }
        return codeBuilder.toString();
    }

}