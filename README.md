# Team Project

Please keep this up-to-date with information about your project throughout the term.

The readme should include information such as:
- a summary of what your application is all about
- a list of the user stories, along with who is responsible for each one
- information about the API(s) that your project uses 
- screenshots or animations demonstrating current functionality

By keeping this README up-to-date,
your team will find it easier to prepare for the final presentation
at the end of the term.
-------------------------------
Presentation TUT0401-BA2200-17 Dec 1 4:00 -> 4:40

Project Specification for Group  TUT0401-17 

Team Name: Team 17

Domain: Online Store

An account-based online store. Users will be able to add items to a cart that they can eventually pay for. 

User Stories: 

User story 1: As a user, I want to be able to add items to my cart, so that I can eventually pay and receive the items.

User story 2: As a user, I want to create an account so that I can personalize my experience and get the access to my points and tokens

User story 3: As a user, I want to give a rating to items so that I can give other customers my advice.

User story 4: As a user, I want to use my points or tokens so that I can reduce the amount of money I pay.

User story 5: As a user, I want to get recommendations or suggestions based on my previous purchases and the deals of the day so that I can shop easily and with knowledge.

User story 6: As a user, I want to be able to create listings for items on the store so that other people can purchase them.

User story 7: As a user, I want to sort and filter products (price, rating, category) so that I can compare options easily.

User story 8: As a user, I want to save items to a wishlist so that I can purchase them later.

User story 9: As a user, I want to add money to my account so I can easily pay for the items in my cart. 

User story 10: As a user, I want to manage my addresses so that checkout is faster and accurate.

User story 11: As a user, I want to be able to add money to my account to buy products.

User story 12: As a customer, I want to apply promo codes or gift cards so that I can save money.





Use Cases:

Use Case 1: Add Item to Cart

Main Flow:

User selects a product.

The System displays product details.

User clicks “Add to Cart,” prompting the system to add the item to the cart and update the total.

Alternative Flows:

Item out of stock → system shows “Out of stock” message.

Use Case 2: Creating an Account

Main Flow:

User selects the Signup Button if they do not have an account.

User input their name, their contact information and a password.

User confirms their password.

User selects the Create button.

User receives a confirmation email.

User confirms their identity.

User has a new account now.

Alternative Flow:

User already has an account so they cannot register a new one so they get a warning that the account exists and they need to log in instead of signing up.

User Case 3: Item Rating

Main Flow:

User should have been signed up.

User selects an item.

User gives stars from 1 to 5 based on their opinion.

User confirms the rating.

User sees the updated item status with the new average rating.

Alternative Flow:

User can undo their rating and give a new one.

User Case 4:   Token Discounts

	Main Flow:

User selects an items they want to buy.

User wants to pay for the item in the cart.

User sees the amount of the tokens or points they can use to lower their cost.

User agrees to use their points or token.

User sees the amount points or tokens used for their purchase in the receipt.

Alternative Flow:

User can see their current amount of tokens or points in a page regardless of their cart being empty or not.

User Case 5: Daily Deals

	Main Flow:

User selects the page for the deals of the day page.

User selects an item there.

User add the item into the cart.

User follows the other steps as they do normally for other types of purchases.

Main Flow for the recommendations:

User sees the products recommended to them by the app based on their previous purchases on the main page.

User selects an item.

User follows the other steps as they do normally for other types of purchases.

User Case 6: Create Listing

Main Flow:

User selects “Create Listing” button

User is prompted to choose an image file for the listing

User submits a name and price for the listing

User can now see the listing on the market view

Alternative flow: 

User selects “Create Listing” button

User is prompted to choose an image file for the listing

Process is canceled because the file is invalid; user is notified

Use Case 7: Search & Filter Products

	Main Flow:

User enters a search term or selects a category..

System returns a list of matching products.

User applies filters (price range, rating, brand) and/or sorting (price, popularity).

System refreshes results accordingly.

Alternative Flow:

No match.

System shows “No results” with suggestions (broaden filters, try similar keywords).

Use Case 8: Login

	Main Flow:

User enters email and password.

System authenticates and starts a session.

Alternative Flow:

Wrong credentials.

System shows error and rate-limits repeated attempts.

Use Case 9: Logout

	Main Flow:

User clicks “Logout.”

System ends session and returns to the home page.

Use Case 10: Manage Shipping/Billing Addresses
	
	Main Flow:
	      
		  - User navigates to “Account then Addresses.”
	      
		  - System displays saved addresses.
	     
		  - User selects “Add Address,” fills in fields, and saves.
	
		 - System validates and saves the new address.

	Alternative Flow:

Address fails validation (missing/invalid fields) then system highlights errors.

User edits or deletes an existing address then system updates list and confirms changes.

Use Case 11: Add Funds (to the user’s Account)

Main Flow:

User selects the “Add Funds” button.

The system displays the current balance, some pre-set options ($5, $10, $20 etc) and the option to choose a custom amount.

System adds the selected amount to the account’s balance.

Alternative Flow:

User inputs an invalid custom option (float with more than 2 decimal places, non-positive number, or a non-numerical character)

System displays a pop-up informing the user they need to input a valid monetary amount (i.e. a positive number with no more than 2 decimal places)

Account balance remains unaffected.

Use Case 12: Apply promotions

Main Flow:

User views cart or checkout page.

The user enters a promo code or gift card number and clicks “Apply.”

The system validates the code, updates totals, and shows the discount or balance used.
    
	  -  Alternative Flow:
	
	- Code invalid/expired or conditions not met then the system shows reason and keeps totals unchanged.

	- Multiple promotions conflict then system applies best eligible discount and explains the rule

Use Case 13: Checkout

Main Flow:

User views cart or checkout page

User selects checkout option

User selects Pay

User confirms payment

Alternative Flow:

The user has insufficient funds.

There is nothing in the cart.

Use Case 14: Clicking on a product to see more information

Main Flow:

User sees a product they are interested in and clicks it to see more information

Leads:

Eric

Use Case #1,5

Ethan

Use Case #8, #9

Zhaoning Zhang (Robert)

Use Case #10, #12

Sam

Use Case #6, #11

John

Use Case #3, 4

Reza

Use Case #2, #7

 

Proposed Entities for the Domain:

Entity 1: User

private name: String

private email: String

private passwordHash: String

private balance: int

public getName(): String

public getEmail(): String

public addBalance(double): void

public removeBalance(double): void

public setPassword(String): void

public checkPassword(String): boolean

Entity 2: Product

private name: String

private price: double

private imageURL: String

private seller: User

public getName(): String

public getPrice(): double

public getImageURL(): String

public getSeller(): User

Entity 3: Cart

private owner: User

private products: Product[]

public getOwner(): User

public getProducts(): Product[]

Entity 4: Order

private customer: User

private products: Product[]

private address: String

private price: double

public getCustomer(): User

public getProducts(): Product[]

public getAddress(): String

public getPrice(): double




Proposed API for the project:

API Name: Custom (Xano)

Main Service Provided: Everything (Customizable)

We will create the necessary database and API

Status: Fully functional
