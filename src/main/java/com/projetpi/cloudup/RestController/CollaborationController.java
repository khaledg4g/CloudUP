package com.projetpi.cloudup.RestController;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.projetpi.cloudup.entities.Collaboration;
import com.projetpi.cloudup.repository.CollaborationRepository;
import com.projetpi.cloudup.service.ICollaboration;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:53289"})
@RestController
@AllArgsConstructor

public class CollaborationController {
    @Autowired
    private ICollaboration iCollaboration;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CollaborationRepository collaborationRepository;

    @PostMapping("/addColbeltaswira")
    public String addCollaboration(@RequestParam("file") MultipartFile file,
                                   @RequestParam("nomcol") String nomcol,
                                   @RequestParam("desccol") String desccol,
                                   @RequestParam("datecol") @DateTimeFormat(pattern = "yyyy-MM-dd") Date datecol,
                                   @RequestParam("placecol") String placecol,
                                   @RequestParam("prixcol") float prixcol,
                                  // @RequestParam("cours_idcour") int cours_idcour,
                                   @RequestParam("partenaires_id_part") int partenaires_id_part)
                                  // @RequestParam("user_iduser") int user_iduser
                                  {
        return iCollaboration.saveCollaboration(file, nomcol,   desccol,   datecol,   placecol,   prixcol,     partenaires_id_part    );
    }






    @PostMapping("/addCollaboration")
    public Collaboration addCollaboration(@RequestBody Collaboration collaboration){
        return iCollaboration.addCollaboration(collaboration);

    }
    @GetMapping("retrieveByIdCollaboration/{idcol}")
    public Collaboration retrieveByIdCollaboration(@PathVariable int idcol){return iCollaboration.retrieveByIdCollaboration(idcol);}


    @GetMapping("/retriveCollaboration")
    public List<Collaboration> retriveCollaboration(){
        return iCollaboration.retriveCollaboration();
    }

    @DeleteMapping("/deleteCollaboration/{idcol}")
    public void deleteCollaboration(@PathVariable int idcol){
        iCollaboration.deleteCollaboration(idcol);
    }


    @PutMapping("/updateCollaboration/{id}")
    public Collaboration updateCol(@RequestBody Collaboration collaboration, @PathVariable int id) {
        collaboration.setIdcol(id); // Ensure ID is set for update
        return iCollaboration.updateCollaboration(collaboration);
    }


    @GetMapping("/momo/{field}")
    public ResponseEntity<List<Collaboration>> getProductsWithSort(@PathVariable String field) {
        List<Collaboration> retrievedCollaborations = iCollaboration.findCollaborationWithSorting(field);
        return ResponseEntity.ok().body(retrievedCollaborations);
    }
    @GetMapping("/paginations/{offset}/{pageSize}")
    public ResponseEntity<List<Collaboration>> getProductsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<Collaboration> retrievedCollaborations = iCollaboration.findCollaborationWithPagination(offset, pageSize);
        return ResponseEntity.ok().body(retrievedCollaborations.getContent());
    }

    @GetMapping("/pagination")
    public Page<Collaboration> GetAllPagination(@RequestParam(defaultValue = "3") int size,
                                              @RequestParam(defaultValue = "0") int page) {
        return iCollaboration.GetAllWithPagination(page,size);
    }

    @GetMapping("/findobjetrec/{objet}")
    public List<Collaboration> RetrieveObjet(@PathVariable String objet)
    {
        return iCollaboration.RetrieveObjet(objet);
    }


    @PutMapping("/{commentId}/upvote")
    public ResponseEntity<?> upvoteCommentary(@PathVariable("commentId") int commentId) {
        iCollaboration.upvoteCollaboration(commentId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{commentId}/downvote")
    public ResponseEntity<?> downvoteCommentary(@PathVariable("commentId") int commentId) {
        iCollaboration.downvoteCollaboration(commentId);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{commentId}/downb")
    public ResponseEntity<?> downnumber(@PathVariable("commentId") int commentId) {
        iCollaboration.downnumberCollaboration(commentId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{commentId}/likes")
    public ResponseEntity<Integer> getCommentLikes(@PathVariable("commentId") int commentId) {
        int likes = iCollaboration.getCollaborationLikes(commentId);
        return ResponseEntity.ok(likes);
    }
    @GetMapping("/{commentId}/dislikes")
    public ResponseEntity<Integer> getCommentDislikes(@PathVariable("commentId") int commentId) {
        int dislikes = iCollaboration.getCollaborationDislikes(commentId);
        return ResponseEntity.ok(dislikes);
    }

    @PostMapping("/collaborationss")
    public ResponseEntity<String> addCollaborationsssssss(@RequestBody Collaboration collaboration) {
        String qrString = generateQRString(collaboration); // Generate QR code string
        collaboration.setQrString(qrString); // Set the QR code string in the collaboration object

        saveCollaboration(collaboration); // Save collaboration data into the database

        return ResponseEntity.ok("Collaboration added successfully");
    }

    private String generateQRString(Collaboration collaboration) {
        // Generate the QR code string based on collaboration data
        // For example, concatenate some fields from the collaboration object
        return collaboration.getNomcol() + collaboration.getPlacecol() + collaboration.getPrixcol();
    }

    private void saveCollaboration(Collaboration collaboration) {
        String sql = "INSERT INTO collaboration (datecol, desccol, imgcol, nomcol, placecol, prixcol, cours_idcour, partenaires_id_part, user_iduser, vote_negatif, vote_positif, qr_string) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, new Object[] {
                    new Timestamp(System.currentTimeMillis()), // Example of current timestamp
                    collaboration.getDesccol(), // Description
                    collaboration.getImgcol(), // Image (you might need to handle image separately)
                    collaboration.getNomcol(), // Name
                    collaboration.getPlacecol(), // Place
                    collaboration.getPrixcol(), // Price
                    collaboration.getCours (), // Course ID
                    collaboration.getPartenaires (), // Partner ID
                    collaboration.getUser (), // User ID
                    collaboration.getVoteNegatif(), // Negative votes
                    collaboration.getVotePositif(), // Positive votes
                    collaboration.getQrString() // QR code string
            }, new int[] {
                    Types.TIMESTAMP, Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR
            });
        } catch (DataAccessException e) {
            // Handle exception
        }
    }






        @GetMapping("/qrcode/{idcol}")
    public void getQRCode(@PathVariable int idcol, HttpServletResponse response) throws IOException, WriterException {
        // Retrieve collaboration information from the database for the specific collaboration ID
        Collaboration collaboration = getCollaborationFromDatabase(idcol);

        // Generate QR code string based on the collaboration information
        String qrString = generateQRStringg(collaboration);

        // Set the width and height of the QR code image
        int width = 500;
        int height = 500;

        // Generate QR code image based on the QR code string and size
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrString, BarcodeFormat.QR_CODE, width, height);

        // Write the QR code image to the response output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.getOutputStream().write(outputStream.toByteArray());
        response.getOutputStream().flush();
    }

    private String generateQRStringg(Collaboration collaboration) {
        // Encode collaboration information into a single string
        // For the image, encode a reference to the image (e.g., URL or file path)
        String qrString = "You have successfully scheduled your reservation. Please show this before entering.\n";
        qrString += " Collaboration Name: " + collaboration.getNomcol() + "\n"
               ;
        // Assuming imgcol contains the image reference


        return qrString;
    }


    private Collaboration getCollaborationFromDatabase(int idcol) {
        // Retrieve collaboration from the repository based on the collaboration ID
        return collaborationRepository.findById(idcol)
                .orElse(null); // Return null if collaboration is not found
    }


}
