package com.example.demo.controller;

import com.example.demo.entity.BookEntity;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.BookListResponse;
import com.example.demo.response.BookResponse;
import com.example.demo.servise.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("api/v1/book")
public class BookController {

    private BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<BaseResponse> getAll(){
        return ResponseEntity.ok(new BookListResponse(service.getAll()));
    }


    @PostMapping("/add")
    public ResponseEntity <BookResponse> registration(@Valid @RequestBody BookEntity data) {
        try {
            BookEntity temp = service.save(data);
            return ResponseEntity.ok(new BookResponse(true, "Книга добавлена.",temp));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BookResponse(false, e.getMessage(),null));
        }
    }


    @PostMapping("/update")
    public ResponseEntity <BaseResponse> update(@RequestBody BookEntity data) {
        try {
            service.save(data);
            return ResponseEntity.ok(new BaseResponse(true, "В книгу внесены изменения."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(false, e.getMessage()));
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(new BaseResponse(true, "Книга была успешно удалена."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(false, e.getMessage()));
        }
    }
}