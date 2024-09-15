package com.belunin.avito_test_task.Controllers;

import com.belunin.avito_test_task.Models.Bid;
import com.belunin.avito_test_task.Models.Dtos.BidDTO;
import com.belunin.avito_test_task.Models.Dtos.ReviewDTO;
import com.belunin.avito_test_task.Models.ResponseTypes.BidApprovalResponse;
import com.belunin.avito_test_task.Models.Review;
import com.belunin.avito_test_task.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bids")
public class BidController {
    private final BidService bidService;
    private final TenderService tenderService;
    private final OrganizationService organizationService;
    private final EmployeeService employeeService;
    private final ApprovalService approvalService;
    private final ReviewService reviewService;
    private final AuthorizationService authorizationService;

    @Autowired
    public BidController(
            TenderService tenderService,
            BidService bidService,
            OrganizationService organizationService,
            EmployeeService employeeService,
            ApprovalService approvalService,
            ReviewService reviewService,
            AuthorizationService authorizationService
    ) {
        this.bidService = bidService;
        this.tenderService = tenderService;
        this.organizationService = organizationService;
        this.employeeService = employeeService;
        this.approvalService = approvalService;
        this.reviewService = reviewService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<List<BidDTO>> getAllBids() {
        List<BidDTO> bids = bidService.getAllBids()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bids);
    }

    // Endpoint Group 06 - Create New Bid
    @PostMapping("/new")
    public ResponseEntity<BidDTO> createBid(@RequestBody BidDTO bidDto) {
        Bid bid = convertToEntity(bidDto);
        Bid createdBid = bidService.createBid(bid);

        return new ResponseEntity<>(convertToDTO(createdBid), HttpStatus.CREATED);
    }

    // Endpoint Group 07 - Submit decision on a bid
    @PostMapping("/{bidId}/submit_decision")
    public ResponseEntity<BidApprovalResponse> submitBidDecision(
            @PathVariable UUID bidId,
            @RequestParam UUID userId,
            @RequestParam boolean isApproved
    ) {
        BidApprovalResponse response = approvalService.approveOrRejectBid(bidId, userId, isApproved);

        return ResponseEntity.ok(response);
    }

    // Endpoint Group 08 - List bids
    @GetMapping("/list")
    public ResponseEntity<List<BidDTO>> listAllBids() {
        List<BidDTO> bids = bidService.getAllBids()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bids);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BidDTO> getBidById(@PathVariable UUID id) {
        return bidService.getBidById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint Group 10 - Edit and Rollback Bids
    @PatchMapping("/{bidId}/edit")
    public ResponseEntity<BidDTO> updateBid(
            @PathVariable UUID bidId,
            @RequestBody BidDTO bidDto
    ) {
        Bid bid = convertToEntity(bidDto);

        return bidService.updateBid(bidId, bid)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{bidId}/rollback/{version}")
    public ResponseEntity<BidDTO> rollbackBid(
            @PathVariable UUID bidId,
            @PathVariable int version
    ) {
        var rolledBackBidOptional = bidService.rollbackBid(bidId, version);
        if (rolledBackBidOptional.isPresent()) {
            var rolledBackBid = rolledBackBidOptional.get();

            return ResponseEntity.ok(convertToDTO(rolledBackBid));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{bidId}/status")
    public ResponseEntity<String> getBidStatus(@PathVariable UUID bidId) {
        var bidOptional = bidService.getBidById(bidId);
        if (bidOptional.isPresent()) {
            var bid = bidOptional.get();
            return ResponseEntity.ok(bid.getStatus().toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{bidId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getBidReviews(@PathVariable UUID bidId) {
        List<Review> reviews = reviewService.findAllByBidId(bidId);
        if (reviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ReviewDTO> reviewDTOs = reviews.stream().map(this::convertToReviewDTO).collect(Collectors.toList());
        return ResponseEntity.ok(reviewDTOs);
    }

    @PostMapping("/{bidId}/feedback")
    public ResponseEntity<ReviewDTO> postFeedback(
            @PathVariable UUID bidId,
            @RequestBody ReviewDTO reviewDTO,
            @RequestParam UUID userId) {
        if (!authorizationService.checkAuthorization(userId, bidId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Review review = convertToReviewEntity(reviewDTO);
        Review savedReview = reviewService.createReview(review);

        return new ResponseEntity<>(convertToReviewDTO(savedReview), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBid(@PathVariable UUID id) {
        bidService.deleteBid(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-tender/{tenderId}")
    public ResponseEntity<List<BidDTO>> getBidsByTenderId(@PathVariable UUID tenderId) {
        List<BidDTO> bids = bidService.getBidsByTenderId(tenderId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bids);
    }

    @GetMapping("/my")
    public ResponseEntity<List<BidDTO>> getBidsByCreatorUsername(@RequestParam String username) {
        List<BidDTO> bids = bidService.getBidsByCreatorUsername(username)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bids);
    }

    private BidDTO convertToDTO(Bid bid) {
        BidDTO dto = new BidDTO();
        dto.setId(bid.getId());
        dto.setName(bid.getName());
        dto.setDescription(bid.getDescription());
        dto.setStatus(bid.getStatus());
        dto.setVersion(bid.getVersion());
        dto.setCreatedAt(bid.getCreatedAt());
        dto.setUpdatedAt(bid.getUpdatedAt());
        dto.setTenderId(bid.getTender().getId());
        dto.setOrganizationId(bid.getOrganization().getId());
        dto.setCreatorId(bid.getCreator().getId());

        return dto;
    }

    private Bid convertToEntity(BidDTO dto) {
        Bid bid = new Bid();
        bid.setId(dto.getId());
        bid.setName(dto.getName());
        bid.setDescription(dto.getDescription());
        bid.setTender(tenderService.findTenderById(dto.getTenderId()));
        bid.setOrganization(organizationService.findOrganizationById(dto.getOrganizationId()));
        bid.setCreator(employeeService.getUserById(dto.getCreatorId()));
        bid.setStatus(dto.getStatus());
        bid.setVersion(dto.getVersion());
        bid.setCreatedAt(dto.getCreatedAt());
        bid.setUpdatedAt(dto.getUpdatedAt());

        return bid;
    }

    private ReviewDTO convertToReviewDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setReviewerId(review.getReviewer().getId());
        dto.setBidId(review.getBid().getId());
        dto.setReviewText(review.getReviewText());
        dto.setCreatedAt(review.getCreatedAt());

        return dto;
    }

    private Review convertToReviewEntity(ReviewDTO dto) {
        Review review = new Review();
        review.setId(dto.getId());
        review.setReviewer(employeeService.getUserById(dto.getReviewerId()));
        review.setBid(bidService.getBidById(dto.getBidId()).orElseThrow());
        review.setReviewText(dto.getReviewText());

        return review;
    }

}

