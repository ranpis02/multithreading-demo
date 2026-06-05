#include <iostream>
#include <memory>

using namespace std;

struct Pet;

struct Person
{
  shared_ptr<Pet> pet_ptr;
  ~Person()
  {
    cout << "Person destroyed" << endl;
  }
};

struct Pet
{
  weak_ptr<Person> owner_ptr;

  ~Pet()
  {
    cout << "Pet destroyed" << endl;
  }
};

int main()
{
  shared_ptr<Person> person = make_shared<Person>();
  shared_ptr<Pet> pet = make_shared<Pet>();

  cout << "person use_count: " << person.use_count() << endl;
  cout << "Pet use_count: " << pet.use_count() << endl;

  person->pet_ptr = pet;
  pet->owner_ptr = person;

  return 0;
}